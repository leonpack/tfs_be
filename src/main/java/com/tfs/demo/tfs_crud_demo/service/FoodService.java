package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FoodService {

    public List<Food> getAllFood();

    public Food getFoodById(int id);

    public void saveFood(Food theFood);

    public void disableFood(int id);

    public void enableFood(int id);
}
