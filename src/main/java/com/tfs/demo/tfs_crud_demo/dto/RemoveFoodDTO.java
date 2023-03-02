package com.tfs.demo.tfs_crud_demo.dto;

public class RemoveFoodDTO {

    private int food_id;
    private int event_id;

    public RemoveFoodDTO(){

    }

    public RemoveFoodDTO(int food_id, int event_id) {
        this.food_id = food_id;
        this.event_id = event_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
