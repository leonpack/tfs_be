package com.tfs.demo.tfs_crud_demo.dto;

import java.util.Date;

public class RevenueByDateByRestaurant {

    private Integer restaurantId;
    private Date revenueDate;

    public RevenueByDateByRestaurant(){

    }

    public RevenueByDateByRestaurant(Integer restaurantId, Date revenueDate) {
        this.restaurantId = restaurantId;
        this.revenueDate = revenueDate;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }
}
