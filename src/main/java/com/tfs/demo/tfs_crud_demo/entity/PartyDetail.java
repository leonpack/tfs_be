package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Entity
@Table(name = "party_detail")
public class PartyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "food_id")
    private Integer foodId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "food_img")
    private String foodImage;

    @Column(name = "food_quantity")
    private Integer foodQuantity;

    @ManyToOne
    @JoinColumn(name = "party_id")
    @JsonBackReference
    private Party party;

    public PartyDetail(){

    }

    public PartyDetail(Integer foodId, String foodName, String foodImage, Integer foodQuantity) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodQuantity = foodQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public Integer getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(Integer foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
