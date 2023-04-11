package com.tfs.demo.tfs_crud_demo.dto;

public class ChangeRestaurantDTO {

    private int orderId;
    private int restaurantId;

    public ChangeRestaurantDTO(){

    }

    public ChangeRestaurantDTO(int orderId, int restaurantId) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
