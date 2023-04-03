package com.tfs.demo.tfs_crud_demo.dto;

public class NewRefundDTO {

    private int orderId;

    private Long amount;


    public NewRefundDTO(){

    }

    public NewRefundDTO(int orderId, Long amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
