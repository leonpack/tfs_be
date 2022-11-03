package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodRestController {
    private FoodService foodService;
    @Autowired
    public FoodRestController(FoodService theFoodService){
        foodService = theFoodService;
    }

    @GetMapping("/foods")
    @ApiOperation("Return list of all foods")
    public List<Food> getAllFood(){
        return foodService.getAllFood();
    }

    @GetMapping("/foods/{foodId}")
    @ApiOperation("Return food based on foodId")
    public Food theFood(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        if(theFood == null){
            throw new RuntimeException("Food with id - "+foodId +" not found");
        }
        return theFood;
    }

    @PostMapping("/foods")
    @ApiOperation("Add new food (need full Food's Json)")
    public Food addNewFood(@RequestBody Food food){
        food.setId(0);
        foodService.saveFood(food);
        return food;
    }

    @PutMapping("/foods")
    @ApiOperation("update existing food (need full Food's Json)")
    public Food updateTheFood(@RequestBody Food food){
        foodService.saveFood(food);
        return food;
    }

    @DeleteMapping("/foods/{foodId}")
    @ApiOperation("disable existing food based on foodId")
    public String disableFood(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        if(theFood == null){
            throw new RuntimeException("Food with id - " +foodId+" not found");
        }
        foodService.disableFood(foodId);
        return "Disable food with id: " +foodId + " completed!!";
    }
}
