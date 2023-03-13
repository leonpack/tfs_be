package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;

public class RevenueByDateByRestaurant {

    private Integer restaurantId;
    private LocalDate revenueDate;

    public RevenueByDateByRestaurant(){

    }

    public RevenueByDateByRestaurant(Integer restaurantId, LocalDate revenueDate) {
        this.restaurantId = restaurantId;
        this.revenueDate = revenueDate;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(LocalDate revenueDate) {
        this.revenueDate = revenueDate;
    }
}
