package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;
@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private int id;

    @Column(name = "promotion_name")
    private String promotionName;

    @Column(name = "promotion_code")
    private String promotionCode;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "status")
    private Boolean status;

    public Promotion(){

    }

    public Promotion(String promotionName, String promotionCode, Integer eventId, int discountPercent, Boolean status) {
        this.promotionName = promotionName;
        this.promotionCode = promotionCode;
        this.eventId = eventId;
        this.discountPercent = discountPercent;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
