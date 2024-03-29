package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.FoodRepository;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImplementation implements FoodService{

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImplementation(FoodRepository theFoodRepository){
        foodRepository = theFoodRepository;
    }


    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public List<Food> getBestSellerFoods(int number) {
        return foodRepository.findFoodsByPurchaseNumGreaterThan(number);
    }

    @Override
    public List<Food> getComboFromFood(Boolean type) {
        return foodRepository.findAllByType(type);
    }

    @Override
    public Food getFoodById(int id) {
        Optional<Food> result = foodRepository.findById(id);

        Food theFood;

        if(result.isPresent()){
            theFood = result.get();
        }
        else{
            throw new RuntimeException("Food with id - " +id +" not found.");
        }
        return theFood;
    }

    @Override
    public Food getByName(String name) {
        return foodRepository.findFoodByFoodName(name);
    }

    @Override
    public void saveFood(Food theFood) {
        foodRepository.save(theFood);
    }

    @Override
    public void disableFood(int id) {
        Optional<Food> result = foodRepository.findById(id);

        Food theFood;

        if(result.isPresent()){
            theFood = result.get();
        }
        else {
            throw new RuntimeException("Food with id - " +id + " not found.");
        }
        theFood.setStatus(false);
        foodRepository.save(theFood);
    }

    @Override
    public void removeFood(int foodId) {
        Optional<Food> result = foodRepository.findById(foodId);
        Food food;
        if(result.isPresent()){
            food = result.get();
            foodRepository.delete(food);
        }
        else {
            throw new RuntimeException("Food with id - " +foodId + " not found!");
        }
    }

}
