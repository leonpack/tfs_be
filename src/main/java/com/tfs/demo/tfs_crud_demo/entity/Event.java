package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "event_id")
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
    private boolean status;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "eventdetail", joinColumns = @JoinColumn(name = "event_id"),inverseJoinColumns = @JoinColumn(name = "food_id"))
//    @JsonManagedReference(value = "event-food")
    private List<Food> foodListFromEvent;

    public Event(){

    }

    public Event(String eventId, String eventName, String description, String image_url, Date fromDate, Date toDate, boolean status, List<Food> foodListFromEvent) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.image_url = image_url;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.foodListFromEvent = foodListFromEvent;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Food> getFoodListFromEvent() {
        return foodListFromEvent;
    }

    public void setFoodListFromEvent(List<Food> foodListFromEvent) {
        this.foodListFromEvent = foodListFromEvent;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", status=" + status +
                ", foodListFromEvent=" + foodListFromEvent +
                '}';
    }

    public void addFood(Food theFood){
        if(foodListFromEvent==null){
            foodListFromEvent = new ArrayList<>();
        }
        foodListFromEvent.add(theFood);
    }
}
