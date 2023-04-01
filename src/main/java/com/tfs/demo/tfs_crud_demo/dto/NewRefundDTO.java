package com.tfs.demo.tfs_crud_demo.dto;

public class NewRefundDTO {

    private int orderId;

    private Double amount;


    public NewRefundDTO(){

    }

    public NewRefundDTO(int orderId, Double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
