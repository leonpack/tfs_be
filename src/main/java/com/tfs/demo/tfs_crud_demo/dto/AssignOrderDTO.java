package com.tfs.demo.tfs_crud_demo.dto;

public class AssignOrderDTO {

    private int staffId;

    private int orderId;

    private String status;
    private String reason;

    public AssignOrderDTO(){

    }

    public AssignOrderDTO(int staffId, int orderId, String status, String reason) {
        this.staffId = staffId;
        this.orderId = orderId;
        this.status = status;
        this.reason = reason;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
