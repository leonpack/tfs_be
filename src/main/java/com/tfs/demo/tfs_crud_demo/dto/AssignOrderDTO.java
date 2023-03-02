package com.tfs.demo.tfs_crud_demo.dto;

public class AssignOrderDTO {

    private String staffId;

    private int orderId;

    public AssignOrderDTO(){

    }

    public AssignOrderDTO(String staffId, int orderId) {
        this.staffId = staffId;
        this.orderId = orderId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
