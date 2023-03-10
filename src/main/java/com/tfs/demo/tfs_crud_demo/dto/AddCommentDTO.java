package com.tfs.demo.tfs_crud_demo.dto;

public class AddCommentDTO {

    private int foodId;
    private String accountId;
    private String comment;
    private Integer point;

    public AddCommentDTO(){

    }

    public AddCommentDTO(int foodId, String accountId, String comment, Integer point) {
        this.foodId = foodId;
        this.accountId = accountId;
        this.comment = comment;
        this.point = point;
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
