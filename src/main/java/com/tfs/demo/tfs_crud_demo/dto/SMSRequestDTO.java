package com.tfs.demo.tfs_crud_demo.dto;

public class SMSRequestDTO {

    private final String phoneNumber;

    public SMSRequestDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
