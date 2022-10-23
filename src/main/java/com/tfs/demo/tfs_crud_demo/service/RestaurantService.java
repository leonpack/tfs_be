package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    public List<Restaurant> getAllRestaurants();

    public Restaurant getRestaurantById(String restaurantId);

    public void saveRestaurant(Restaurant theRestaurant);

    public void disableRestaurant(String restaurantId);

    public boolean checkDuplicateId(String checkRestaurantId);

    public boolean checkDuplicatePhoneNumber(String checkPhoneNumber);

    public boolean checkDuplicateName(String checkRestaurantName);

}
