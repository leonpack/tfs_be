package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Food> getAllFood(){
        return foodService.getAllFood();
    }

    @GetMapping("/foods/{foodId}")
    public Food getFoodById(@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);

        if (theFood == null) {
            throw new RuntimeException("Food with id - " + foodId + " not found");
        }

        return theFood;
    }
}
