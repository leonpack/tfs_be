package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne()
    @JoinColumn(name = "food_id")
    @JsonIgnoreProperties({"listComment","description","imgUrl","theCategory","theRegion","status","purchaseNum","eventList","price"})
    private Food food;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "status")
    private Boolean status;

    public Feedback()
    {

    }

    public Feedback(Food food, String accountId, String comment, Integer rate, Boolean status) {
        this.food = food;
        this.accountId = accountId;
        this.comment = comment;
        this.rate = rate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
