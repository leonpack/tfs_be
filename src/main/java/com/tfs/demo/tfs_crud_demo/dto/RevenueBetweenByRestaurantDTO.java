package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;

public class RevenueBetweenByRestaurantDTO {

    private Integer restaurantId;

    private LocalDate fromDate;

    private LocalDate toDate;

    public RevenueBetweenByRestaurantDTO(){

    }

    public RevenueBetweenByRestaurantDTO(Integer restaurantId, LocalDate fromDate, LocalDate toDate) {
        this.restaurantId = restaurantId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
