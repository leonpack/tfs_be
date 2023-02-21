package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "staff_full_name")
    private String staffFullName;

    @Column(name = "staff_email")
    private String staffEmail;

    @Column(name = "staff_avatar_url")
    private String staffAvatarUrl;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id")
    @JsonManagedReference(value = "account-staff")
    private Account theAccountForStaff;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @JsonBackReference(value = "restaurant-staff")
    private Restaurant theRestaurant;

    @Column(name = "activity_status")
    private String staffActivityStatus;

    @Column(name = "status")
    private boolean staffStatus;

    public Staff(){

    }

    public Staff(String staffId, String staffFullName, String staffEmail, String staffAvatarUrl, Account theAccountForStaff, Restaurant theRestaurants, String staffActivityStatus, boolean staffStatus) {
        this.staffId = staffId;
        this.staffFullName = staffFullName;
        this.staffEmail = staffEmail;
        this.staffAvatarUrl = staffAvatarUrl;
        this.theAccountForStaff = theAccountForStaff;
        this.theRestaurant = theRestaurant;
        this.staffActivityStatus = staffActivityStatus;
        this.staffStatus = staffStatus;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
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
