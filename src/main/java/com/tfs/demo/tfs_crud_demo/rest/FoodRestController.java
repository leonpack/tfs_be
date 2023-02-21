package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import com.tfs.demo.tfs_crud_demo.utils.GlobalExceptionHandler;
import com.tfs.demo.tfs_crud_demo.utils.GlobalExceptionResponse;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FoodRestController {
    private FoodService foodService;
    @Autowired
    public FoodRestController(FoodService theFoodService){
        foodService = theFoodService;
    }

    @GetMapping("/foods")
    public List<Food> getAllFood(){
        return foodService.getAllFood();
    }

    @GetMapping("/foods/{foodId}")
    public Food theFood(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        if(theFood == null){
            throw new RuntimeException("Food with id - "+foodId +" not found");
        }
        return theFood;
    }

    @GetMapping("/foods/detail/{foodId}")
    public List<Object[]> getFoodDetail(@PathVariable int foodId){
        return foodService.getFoodDetail(foodId);
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
            food.setFoodName(theFood1.getDescription());
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
}
