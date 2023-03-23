package com.tfs.demo.tfs_crud_demo.dto;

public class FeedbackStatusDTO {

    private int orderId;
    private Boolean feedbackStatus;

    public FeedbackStatusDTO(){

    }

    public FeedbackStatusDTO(int orderId, Boolean feedbackStatus) {
        this.orderId = orderId;
        this.feedbackStatus = feedbackStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Boolean getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(Boolean feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }
}
