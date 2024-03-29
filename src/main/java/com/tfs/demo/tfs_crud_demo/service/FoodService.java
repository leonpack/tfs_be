package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Food;

import java.util.List;

public interface FoodService {

    public List<Food> getAllFood();

    public List<Food> getBestSellerFoods(int number);

    public List<Food> getComboFromFood(Boolean type);

    public Food getFoodById(int id);

    public Food getByName(String name);

    public void saveFood(Food theFood);

    public void disableFood(int id);

    public void removeFood(int foodId);
}
