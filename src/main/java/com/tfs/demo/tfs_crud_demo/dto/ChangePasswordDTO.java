package com.tfs.demo.tfs_crud_demo.dto;

public class ChangePasswordDTO {

    private String phoneNumber;
    private String password;

    public ChangePasswordDTO(){

    }

    public ChangePasswordDTO(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
