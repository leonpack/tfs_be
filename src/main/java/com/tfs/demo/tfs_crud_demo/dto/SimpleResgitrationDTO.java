package com.tfs.demo.tfs_crud_demo.dto;

public class SimpleResgitrationDTO {

    private String phoneNumber;
    private String fullName;

    public SimpleResgitrationDTO(){

    }

    public SimpleResgitrationDTO(String phoneNumber, String fullName) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
