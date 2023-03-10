package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.EventRepository;
import com.tfs.demo.tfs_crud_demo.dao.FoodRepository;
import com.tfs.demo.tfs_crud_demo.dto.RemoveFoodDTO;
import com.tfs.demo.tfs_crud_demo.entity.Event;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.EventService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FoodRestController {
    private FoodService foodService;
    private EventService eventService;
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

    @GetMapping("/foods/paging")
    public List<Food> getAllFood(@RequestParam int size){
        return foodService.getAllFoodWithPaging(size);
    }

    @GetMapping("/foods/bestseller")
    public List<Food> getBestSellers(){
        return foodService.getBestSellerFoods(50);
    }

    @GetMapping("/foods/{foodId}")
    public Food theFood(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        if(theFood == null){
            throw new RuntimeException("Food with id - "+foodId +" not found");
        }
        return theFood;
    }

    @PostMapping("/foods")
    public Food addNewFood(@RequestBody Food food){
        food.setId(0);
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
        if(String.valueOf(food.getPrice())==null){
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
