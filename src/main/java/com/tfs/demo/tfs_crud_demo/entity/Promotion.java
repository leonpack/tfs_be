package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
@Entity
@Table(name = "Promotion")
public class Promotion {

    @Id
    @Column(name = "promotion_code")
    private String promotionCode;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "promotion_quantity")
    private int promotionQuantity;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "status")
    private boolean status;

    public Promotion(){

    }

    public Promotion(String promotionCode, String eventId, int promotionQuantity, int discountPercent, boolean status) {
        this.promotionCode = promotionCode;
        this.eventId = eventId;
        this.promotionQuantity = promotionQuantity;
        this.discountPercent = discountPercent;
        this.status = status;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public void setPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
