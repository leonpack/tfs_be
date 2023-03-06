package com.tfs.demo.tfs_crud_demo.dto;

import java.util.Date;

public class RevenueBetweenByRestaurantDTO {

    private Integer restaurantId;

    private Date fromDate;

    private Date toDate;

    public RevenueBetweenByRestaurantDTO(){

    }

    public RevenueBetweenByRestaurantDTO(Integer restaurantId, Date fromDate, Date toDate) {
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
