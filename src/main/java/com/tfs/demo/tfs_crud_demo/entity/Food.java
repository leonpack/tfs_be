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
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
//    private int id;
    private int id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image_url")
    private String imgUrl;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    @JsonIgnoreProperties({"foodList","status"})
//    @JsonBackReference(value = "category-food")
    private Category theCategory;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "region_id",referencedColumnName = "region_id")
//    @JsonBackReference(value = "region-food")
    @JsonIgnoreProperties("foodList")
    private Region theRegion;

    @Column(name = "status")
    private boolean status;

    @Column(name = "purchase_num")
    private Integer purchaseNum;

    @ManyToMany(mappedBy = "foodList")
    @JsonIgnoreProperties({"foodList","description","image_url","fromDate","toDate","status"})
    private Set<Event> eventList = new HashSet<Event>();

    public Food(){

    }

//    public Food(String foodName, String description, double price, String imgUrl, Category theCategory, Region theRegion, boolean status, Integer purchaseNum, Set<Event> eventList) {
//        this.foodName = foodName;
//        this.description = description;
//        this.price = price;
//        this.imgUrl = imgUrl;
//        this.theCategory = theCategory;
//        this.theRegion = theRegion;
//        this.status = status;
//        this.purchaseNum = purchaseNum;
//        this.eventList = eventList;
//    }

    public Food(String foodName, String description, double price, String imgUrl, boolean status, Integer purchaseNum) {
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.status = status;
        this.purchaseNum = purchaseNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Category getTheCategory() {
        return theCategory;
    }

    public void setTheCategory(Category theCategory) {
        this.theCategory = theCategory;
    }

    public Region getTheRegion() {
        return theRegion;
    }

    public void setTheRegion(Region theRegion) {
        this.theRegion = theRegion;
    }

    public Set<Event> getEventList() {
        return eventList;
    }

    public void setEventList(Set<Event> eventList) {
        this.eventList = eventList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }


    public void addEvent(Event theEvent){
        if(eventList == null){
            eventList = new HashSet<Event>();
        }
        eventList.add(theEvent);
    }

    public void remove(Event event){
        this.eventList.remove(event);
        event.getFoodList().remove(this);
    }

}
