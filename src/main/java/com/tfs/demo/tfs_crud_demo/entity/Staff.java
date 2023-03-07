package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private int staffId;

    @Column(name = "staff_full_name")
    private String staffFullName;

    @Column(name = "staff_email")
    private String staffEmail;

    @Column(name = "staff_avatar_url")
    private String staffAvatarUrl;

    @OneToOne()
    @JoinColumn(name = "account_id")
//    @JsonManagedReference(value = "account-staff")
    private Account theAccountForStaff;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
//    @JsonBackReference(value = "restaurant-staff")
    @JsonIgnoreProperties({"staffList","restaurantLocation","latitude","longitude","status","restaurantNumber"})
    private Restaurant theRestaurant;

    @Column(name = "activity_status")
    private String staffActivityStatus;

    @Column(name = "status")
    private boolean staffStatus;

    public Staff(){

    }

    public Staff(String staffFullName, String staffEmail, String staffAvatarUrl, Account theAccountForStaff, Restaurant theRestaurant, String staffActivityStatus, boolean staffStatus) {
        this.staffFullName = staffFullName;
        this.staffEmail = staffEmail;
        this.staffAvatarUrl = staffAvatarUrl;
        this.theAccountForStaff = theAccountForStaff;
        this.theRestaurant = theRestaurant;
        this.staffActivityStatus = staffActivityStatus;
        this.staffStatus = staffStatus;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffFullName() {
        return staffFullName;
    }

    public void setStaffFullName(String staffFullName) {
        this.staffFullName = staffFullName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffAvatarUrl() {
        return staffAvatarUrl;
    }

    public void setStaffAvatarUrl(String staffAvatarUrl) {
        this.staffAvatarUrl = staffAvatarUrl;
    }

    public Account getTheAccountForStaff() {
        return theAccountForStaff;
    }

    public void setTheAccountForStaff(Account theAccountForStaff) {
        this.theAccountForStaff = theAccountForStaff;
    }

    public Restaurant getTheRestaurant() {
        return theRestaurant;
    }

    public void setTheRestaurant(Restaurant theRestaurant) {
        this.theRestaurant = theRestaurant;
    }

    public String getStaffActivityStatus() {
        return staffActivityStatus;
    }

    public void setStaffActivityStatus(String staffActivityStatus) {
        this.staffActivityStatus = staffActivityStatus;
    }

    public boolean isStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(boolean staffStatus) {
        this.staffStatus = staffStatus;
    }

}
