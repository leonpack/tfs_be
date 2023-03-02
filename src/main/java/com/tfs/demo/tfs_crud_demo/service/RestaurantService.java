package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    public List<Restaurant> getAllRestaurants();

    public Restaurant getRestaurantById(int restaurantId);

    public void saveRestaurant(Restaurant theRestaurant);

    public void disableRestaurant(int restaurantId);

    public boolean checkDuplicatePhoneNumber(String checkPhoneNumber);

}
