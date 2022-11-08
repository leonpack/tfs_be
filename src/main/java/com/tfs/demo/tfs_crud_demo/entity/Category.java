package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "theCategory",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonManagedReference(value = "category-food")
    private List<Food> foodList;

    @Column(name = "status")
    private boolean status;

    public Category(){

    }

    public Category(String id, String categoryName, List<Food> foodList, boolean status) {
        this.id = id;
        this.categoryName = categoryName;
        this.foodList = foodList;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", status=" + status +
                '}';
    }

    public void addFood(Food theFood){
        if(foodList == null){
            foodList = new ArrayList<>();
        }
        foodList.add(theFood);
    }

}
