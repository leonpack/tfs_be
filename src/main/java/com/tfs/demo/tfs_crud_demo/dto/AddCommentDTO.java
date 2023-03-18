package com.tfs.demo.tfs_crud_demo.dto;

public class AddCommentDTO {

    private int foodId;
    private String accountId;
    private String avatarUrl;
    private String comment;
    private Integer rate;

    public AddCommentDTO(){

    }

    public AddCommentDTO(int foodId, String accountId, String avatarUrl, String comment, Integer rate) {
        this.foodId = foodId;
        this.accountId = accountId;
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
}
