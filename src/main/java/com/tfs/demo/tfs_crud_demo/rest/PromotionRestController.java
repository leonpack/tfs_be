package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Event;
import com.tfs.demo.tfs_crud_demo.entity.Promotion;
import com.tfs.demo.tfs_crud_demo.service.EventService;
import com.tfs.demo.tfs_crud_demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PromotionRestController {
    private final PromotionService promotionService;
    private final EventService eventService;

    @Autowired
    public PromotionRestController(PromotionService thePromotionService,EventService theEventService){
        promotionService = thePromotionService;
        eventService = theEventService;
    }

    @GetMapping("/promotions")
    public List<Promotion> getAllPromotion(){
        return promotionService.getAllPromotions();
    }

    @GetMapping("/promotions/bytoday")
    public List<Promotion> getPromotionsByRecentEvent(){
        Date today = new Date(System.currentTimeMillis());
        List<Event> eventList = eventService.getAllEvents();
        List<Promotion> availablePromotion = new ArrayList<>();
        for(Event item: eventList){
            if(item.getFromDate().before(today) && item.getToDate().after(today)){
                Promotion promotion = promotionService.getPromotionByEvent(item.getEventId());
                availablePromotion.add(promotion);
            }
        }
        if(availablePromotion.isEmpty()){
            availablePromotion.add(promotionService.getPromotionById(1));
            availablePromotion.add(promotionService.getPromotionById(2));
            availablePromotion.add(promotionService.getPromotionById(3));
        }
        return availablePromotion;
    }


    @GetMapping("/promotions/{promotionCode}")
    public Promotion getPromotionByCode(@PathVariable String promotionCode){
        Promotion thePromotion = promotionService.getPromotionByCode(promotionCode);
        if(thePromotion==null){
            throw new RuntimeException("Promotion code - " +promotionCode + " not found");
        }
        return thePromotion;
    }

    @PostMapping("/promotions")
    public String addNewPromotion(@RequestBody Promotion thePromotion){
        if(thePromotion.getPromotionName()==null){
            thePromotion.setPromotionName("");
        }
        promotionService.savePromotion(thePromotion);
        return "Saved new promotion successful";
    }

    @PutMapping("/promotions")
    public Promotion updatePromotion(@RequestBody Promotion thePromotion){
        Promotion existPromotion = promotionService.getPromotionById(thePromotion.getId());
        if(thePromotion.getPromotionName()==null){
            thePromotion.setPromotionName(existPromotion.getPromotionName());
        }
        promotionService.savePromotion(thePromotion);
        return thePromotion;
    }

    @DeleteMapping("/promotions/{promotionId}")
    public String disablePromotion(@PathVariable int promotionId){
        Promotion thePromotion = promotionService.getPromotionById(promotionId);
        if(thePromotion == null){
            throw new RuntimeException("Promotion " +promotionId+ " not found!!");
        }
        thePromotion.setStatus(false);
        promotionService.savePromotion(thePromotion);
        return "Disable promotion " +promotionId + " successful!";
    }
}
