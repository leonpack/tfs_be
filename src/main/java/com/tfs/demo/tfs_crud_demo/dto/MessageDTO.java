package com.tfs.demo.tfs_crud_demo.dto;

public class MessageDTO {

    private String title;

    private String message;

    public MessageDTO(){

    }

    public MessageDTO(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
