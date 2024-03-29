package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.EventRepository;
import com.tfs.demo.tfs_crud_demo.dao.FoodRepository;
import com.tfs.demo.tfs_crud_demo.dto.RemoveFoodDTO;
import com.tfs.demo.tfs_crud_demo.entity.Event;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.EventService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FoodRestController {
    private final FoodService foodService;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public FoodRestController(FoodService theFoodService, EventService theEventService,
                              EventRepository eventRepository,
                              FoodRepository foodRepository){
        foodService = theFoodService;
        eventService = theEventService;
        this.eventRepository = eventRepository;
        this.foodRepository = foodRepository;
    }

    @GetMapping("/foods")
    public List<Food> getAllFood(){
        return foodService.getAllFood();
    }

    @GetMapping("/foods/pagination")
    public List<Food> getAllFoodsWithPagination(@RequestParam int page, @RequestParam int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Food> pagedResult = foodRepository.findAll(paging);
        if(pagedResult.hasContent()){
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }


    @GetMapping("/foods/bestseller")
    public List<Food> getBestSellers(){
        return foodService.getBestSellerFoods(50);
    }

    @GetMapping("/foods/combo")
    public List<Food> getComboList(){
        return foodService.getComboFromFood(true);
    }

    @GetMapping("/foods/purefood")
    public List<Food> getPureFoodsList(){
        return foodService.getComboFromFood(false);
    }

    @GetMapping("/foods/{foodId}")
    public Food theFood(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        if(theFood == null){
            throw new RuntimeException("Food with id - "+foodId +" not found");
        }
        return theFood;
    }

//    @GetMapping("/foods/traditional")
//    public List<Food> getTraditionalFoodList(){
//        List<Food> newList = new ArrayList<>();
//        newList.add(foodService.getFoodById(1));
//        newList.add(foodService.getFoodById(6));
//        newList.add(foodService.getFoodById(10));
//        newList.add(foodService.getFoodById(12));
//        newList.add(foodService.getFoodById(13));
//        newList.add(foodService.getFoodById(21));
//        newList.add(foodService.getFoodById(28));
//        newList.add(foodService.getFoodById(32));
//        newList.add(foodService.getFoodById(36));
//        newList.add(foodService.getFoodById(39));
//        newList.add(foodService.getFoodById(41));
//        newList.add(foodService.getFoodById(49));
//        newList.add(foodService.getFoodById(50));
//        newList.add(foodService.getFoodById(52));
//        newList.add(foodService.getFoodById(53));
//        return newList;
//    }

    @GetMapping("/foods/traditional")
    public List<Food> getTraditionalFoodsVersion2(){
        //get event theo ngày sắp tới
        LocalDate today = LocalDate.now();
        List<Event> eventList = eventService.getAllEvents();
        Event existEvent = null;
        for(Event item: eventList){
            LocalDate fromDateOrigin = LocalDate.from(Instant.ofEpochMilli(item.getFromDate().getTime()).atZone(ZoneId.systemDefault()));
            if(ChronoUnit.DAYS.between(today, fromDateOrigin)<=30){
                existEvent = item;
                break;
            }
        }
        List<Food> newList = new ArrayList<>(existEvent.getFoodList());
        if(newList==null || newList.isEmpty()){
            newList.add(foodService.getFoodById(1));
            newList.add(foodService.getFoodById(6));
            newList.add(foodService.getFoodById(10));
            newList.add(foodService.getFoodById(12));
            newList.add(foodService.getFoodById(13));
            newList.add(foodService.getFoodById(21));
            newList.add(foodService.getFoodById(28));
            newList.add(foodService.getFoodById(32));
            newList.add(foodService.getFoodById(36));
            newList.add(foodService.getFoodById(39));
            newList.add(foodService.getFoodById(41));
            newList.add(foodService.getFoodById(49));
            newList.add(foodService.getFoodById(50));
            newList.add(foodService.getFoodById(52));
            newList.add(foodService.getFoodById(53));
        }
        return newList;
    }

    @PostMapping("/foods")
    public Food addNewFood(@RequestBody Food food){
        food.setId(0);
        if(food.getRatingNum()==null){
            food.setRatingNum(0);
        }
        if(food.getType()==null){
            food.setType(false);
        }
        if(food.getImgUrl()==null || food.getImgUrl().isBlank()){
            food.setImgUrl("https://live.staticflickr.com/65535/52821073294_019a4ab45d_m.jpg");
        }
        foodService.saveFood(food);
        return food;
    }

    @PutMapping("/foods")
    public Food updateTheFood(@RequestBody Food food){
        Food theFood1 = foodService.getFoodById(food.getId());

        if(food.getFoodName()==null){
            food.setFoodName(theFood1.getFoodName());
        }
        if(food.getDescription()==null){
            food.setDescription(theFood1.getDescription());
        }
        if(food.getEventList()==null){
            food.setEventList(theFood1.getEventList());
        }
        if(food.getImgUrl()==null){
            food.setImgUrl(theFood1.getImgUrl());
        }
        if(String.valueOf(food.getPrice())==null || String.valueOf(food.getPrice()).isBlank()){
            food.setPrice(theFood1.getPrice());
        }
        if(food.getPurchaseNum()==null){
            food.setPurchaseNum(theFood1.getPurchaseNum());
        }
        if(food.getTheCategory()==null){
            food.setTheCategory(theFood1.getTheCategory());
        }
        if(food.getTheRegion()==null){
            food.setTheRegion(theFood1.getTheRegion());
        }
        if(food.getRatingNum()==null){
            food.setRatingNum(theFood1.getRatingNum());
        }
        if(food.getType()==null){
            food.setType(theFood1.getType());
        }
        foodService.saveFood(food);
        return food;
    }

    @DeleteMapping("/foods/{foodId}")
    public String disableFood(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        if(theFood == null){
            throw new RuntimeException("Food with id - " +foodId+" not found");
        }
        foodService.disableFood(foodId);
        return "Disable food with id: " +foodId + " completed!!";
    }

    @DeleteMapping("/foods/hidden")
    public String removeFood(@RequestBody RemoveFoodDTO removeFoodDTO){
        Food food = foodService.getFoodById(removeFoodDTO.getFood_id());
        Event event = eventService.getEventById(removeFoodDTO.getEvent_id());
        food.setTheCategory(null);
        food.setTheRegion(null);
        for(Food item : event.getFoodList()){
            if(item==food){
                event.remove(item);
                eventRepository.save(event);
            }
        }
        eventRepository.save(event);
        for(Event item : food.getEventList()){
            if(item==event){
                food.remove(item);
                foodRepository.save(food);
            }
        }
        foodRepository.save(food);
        foodService.saveFood(food);
        foodRepository.delete(food);
        return "Deleted!";
    }

}
