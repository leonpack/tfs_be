package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "event_id",updatable = false)
    private String eventId;
    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;
    @Column(name = "status")
    private Boolean status;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "eventdetail", joinColumns = @JoinColumn(name = "event_id"),inverseJoinColumns = @JoinColumn(name = "food_id"))
//    @JsonManagedReference(value = "event-food")
    @JsonIgnoreProperties("eventList")
    private Set<Food> foodList = new HashSet<Food>();

    public Event(){

    }

    public Event(String eventId, String eventName, String description, String image_url, Date fromDate, Date toDate, Boolean status, Set<Food> foodList) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.image_url = image_url;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.foodList = foodList;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(Set<Food> foodList) {
        this.foodList = foodList;
    }

    public void remove(Food food){
        this.foodList.remove(food);
        food.getEventList().remove(this);
    }

}
