package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Food;

import java.util.List;

public interface FoodDAO {

    public List<Food> getAllFoods();

    public Food getFoodById(int id);

    public void saveFood(Food theFood);

    public void deleteFood(int id);

}
