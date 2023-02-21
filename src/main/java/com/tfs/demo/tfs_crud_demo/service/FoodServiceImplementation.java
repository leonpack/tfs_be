package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.FoodRepository;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImplementation implements FoodService{

    private FoodRepository foodRepository;

    @Autowired
    public FoodServiceImplementation(FoodRepository theFoodRepository){
        foodRepository = theFoodRepository;
    }


    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Food getFoodById(int id) {
        Optional<Food> result = foodRepository.findById(id);

        Food theFood = null;

        if(result.isPresent()){
            theFood = result.get();
        }
        else{
            throw new RuntimeException("Food with id - " +id +" not found.");
        }
        return theFood;
    }

    @Override
    public List<Object[]> getFoodDetail(int id) {
        return foodRepository.getFoodDetail(id);
    }


    @Override
    public void saveFood(Food theFood) {
        foodRepository.save(theFood);
    }

    @Override
    public void disableFood(int id) {
        Optional<Food> result = foodRepository.findById(id);

        Food theFood = null;

        if(result.isPresent()){
            theFood = result.get();
        }
        else {
            throw new RuntimeException("Food with id - " +id + " not found.");
        }
        theFood.setStatus(false);
        foodRepository.save(theFood);
    }

    //xài được
    @Override
    public void enableFood(int id) {
        Optional<Food> result = foodRepository.findById(id);

        Food theFood = null;

        if(result.isPresent()){
            theFood = result.get();
        }
        else{
            throw new RuntimeException("Food with id - " + " not found.");
        }
        theFood.setStatus(true);
        foodRepository.save(theFood);
    }

}
