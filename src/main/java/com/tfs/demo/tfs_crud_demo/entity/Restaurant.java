package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "restaurant_location")
    private String restaurantLocation;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_phone_number")
    private String restaurantNumber;

    @Column(name = "status")
    private boolean status;

    public Restaurant(){

    }

    public Restaurant(String restaurantId, String restaurantLocation, String restaurantName, String restaurantNumber, boolean status) {
        this.restaurantId = restaurantId;
        this.restaurantLocation = restaurantLocation;
        this.restaurantName = restaurantName;
        this.restaurantNumber = restaurantNumber;
        this.status = status;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(String restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId='" + restaurantId + '\'' +
                ", restaurantLocation='" + restaurantLocation + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantNumber='" + restaurantNumber + '\'' +
                ", status=" + status +
                '}';
    }

}
