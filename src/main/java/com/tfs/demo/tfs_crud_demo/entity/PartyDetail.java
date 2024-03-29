package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "food_price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "party_id")
    @JsonBackReference
    private Party party;

    public PartyDetail(){

    }

    public PartyDetail(Integer foodId, String foodName, String foodImage, Double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.price = price;
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

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
