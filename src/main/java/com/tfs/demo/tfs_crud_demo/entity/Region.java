package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "region")
public class Region {

    @Id
    @Column(name = "region_id")
    private String id;

    @Column(name = "region_name")
    private String region_name;

    @OneToMany(mappedBy = "theRegion",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JsonManagedReference(value = "region-food")
    @JsonIgnoreProperties("theRegion")
    private List<Food> foodList;

    public Region(){

    }

    public Region(String id, String region_name, List<Food> foodList) {
        this.id = id;
        this.region_name = region_name;
        this.foodList = foodList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", region_name='" + region_name + '\'' +
                ", foodList=" + foodList +
                '}';
    }

    public void addFood(Food theFood){
        if(foodList == null){
            foodList = new ArrayList<>();
        }
        foodList.add(theFood);
    }

}
