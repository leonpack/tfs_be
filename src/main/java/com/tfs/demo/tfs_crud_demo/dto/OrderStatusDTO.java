package com.tfs.demo.tfs_crud_demo.dto;

public class OrderStatusDTO {

    private int orderId;

    private String status;

    public OrderStatusDTO(){

    }

    public OrderStatusDTO(int orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
