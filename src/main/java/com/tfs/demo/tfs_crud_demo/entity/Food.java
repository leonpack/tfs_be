package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JsonBackReference(value = "category-food")
    private Category theCategory;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "region_id",referencedColumnName = "region_id")
    @JsonBackReference(value = "region-food")
    private Region theRegion;

    @Column(name = "status")
    private boolean status;

    @Column(name = "purchase_num")
    private Integer purchaseNum;

    @ManyToMany(mappedBy = "foodListFromEvent",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JsonBackReference(value = "event-food")
    @JsonIgnoreProperties("foodListFromEvent")
    @JsonIgnore
    private List<Event> eventList;


    public Food(){

    }

    public Food(String foodName, String description, double price, String imgUrl, Category theCategory, Region theRegion, boolean status, Integer purchaseNum, List<Event> eventList) {
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.theCategory = theCategory;
        this.theRegion = theRegion;
        this.status = status;
        this.purchaseNum = purchaseNum;
        this.eventList = eventList;
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

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
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
            eventList = new ArrayList<>();
        }
        eventList.add(theEvent);
    }

}
