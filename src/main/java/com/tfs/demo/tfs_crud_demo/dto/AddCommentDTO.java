package com.tfs.demo.tfs_crud_demo.dto;

public class AddCommentDTO {

    private int foodId;
    private int orderId;
    private Integer customerId;
    private String avatarUrl;
    private String comment;
    private Integer rate;

    public AddCommentDTO(){

    }

    public AddCommentDTO(int foodId, int orderId, Integer customerId, String avatarUrl, String comment, Integer rate) {
        this.foodId = foodId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.avatarUrl = avatarUrl;
        this.comment = comment;
        this.rate = rate;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
