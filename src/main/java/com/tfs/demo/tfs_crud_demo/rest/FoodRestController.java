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

    @PostMapping("/foods")
    public Food addNewFood(@RequestBody Food food){
        food.setId(0);
        foodService.saveFood(food);
        return food;
    }

    @PutMapping("/foods")
    public Food updateTheFood(@RequestBody Food food){
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
