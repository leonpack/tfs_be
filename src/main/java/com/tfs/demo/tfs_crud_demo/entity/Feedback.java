package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;

@Entity
@Table
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "food_id")
    private Integer foodId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "point")
    private Integer point;

    @Column(name = "status")
    private Boolean status;

    public Feedback()
    {

    }

    public Feedback(Integer foodId, Integer customerId, String comment, Integer point, Boolean status) {
        this.foodId = foodId;
        this.customerId = customerId;
        this.comment = comment;
        this.point = point;
        this.status = status;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
