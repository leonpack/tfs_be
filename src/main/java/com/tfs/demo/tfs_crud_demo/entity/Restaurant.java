package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "restaurant_location")
    private String restaurantLocation;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_phone_number")
    private String restaurantNumber;

    @OneToMany(mappedBy = "theRestaurant")
    @JsonManagedReference(value = "restaurant-staff")
    private List<Staff> staffList;

    @Column(name = "status")
    private boolean status;

    public Restaurant(){

    }

    public Restaurant(String restaurantId, String restaurantLocation, String latitude, String longitude, String restaurantName, String restaurantNumber, boolean status) {
        this.restaurantId = restaurantId;
        this.restaurantLocation = restaurantLocation;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
