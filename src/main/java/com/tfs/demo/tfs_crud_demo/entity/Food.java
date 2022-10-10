package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image_url")
    private String imgUrl;

    @Column(name = "category_id")
    private String category_id;

    @Column(name = "region_id")
    private String region_id;

    @Column(name = "event_id")
    private String event_id;

    @Column(name = "status")
    private boolean status;

    public Food(){

    }

    public Food(String foodName, String description, double price, String imgUrl, String category_id, String region_id, String event_id, boolean status) {
        this.foodName = foodName;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category_id = category_id;
        this.region_id = region_id;
        this.event_id = event_id;
        this.status = status;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imgUrl='" + imgUrl + '\'' +
                ", category_id='" + category_id + '\'' +
                ", region_id='" + region_id + '\'' +
                ", event_id='" + event_id + '\'' +
                ", status=" + status +
                '}';
    }
}
