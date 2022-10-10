package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.FoodDAO;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImplementation implements FoodService{

    private FoodDAO foodDao;

    @Autowired
    public FoodServiceImplementation(FoodDAO theFoodDao){
        foodDao = theFoodDao;
    }

    @Override
    public List<Food> getAllFood() {
        return foodDao.getAllFoods();
    }

    @Override
    public Food getFoodById(int id) {
        return foodDao.getFoodById(id);
    }
}
