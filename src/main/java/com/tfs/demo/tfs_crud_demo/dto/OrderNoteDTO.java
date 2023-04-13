package com.tfs.demo.tfs_crud_demo.dto;

public class OrderNoteDTO {

    private int orderId;

    private String note;

    public OrderNoteDTO(){

    }

    public OrderNoteDTO(int orderId, String note) {
        this.orderId = orderId;
        this.note = note;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
