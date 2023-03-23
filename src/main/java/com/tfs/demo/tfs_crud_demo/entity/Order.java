package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "promotion_id")
    private Integer promotionId;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_date")
    @CreationTimestamp
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "received_time")
    private LocalDateTime receiveTime;

    @Column(name = "activity_status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "reason")
    private String reason;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @JoinColumn(name = "customer_id")
    private Integer customerId;

    @JoinColumn(name = "restaurant_id")
    private Integer restaurantId;

    @JoinColumn(name = "staff_id")
    private Integer staffId;

    @OneToMany(mappedBy = "theOrder",cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetail> itemList = new ArrayList<>();


    @OneToMany(mappedBy = "orderService", cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderServices> serviceList = new ArrayList<>();


    @OneToMany(mappedBy = "orderCombo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderComboDetail> comboList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "party_id")
    private Party party;

    @Column(name = "is_feedback")
    private Boolean feedbackStatus;

    public Order(){

    }

    public Order(Integer promotionId, Double totalPrice, Integer totalQuantity, String paymentMethod, String deliveryAddress, LocalDateTime orderDate, LocalDateTime deliveryDate, LocalDateTime receiveTime, String status, String note, String reason, String deliveryMethod, Integer customerId, Integer restaurantId, Integer staffId, Boolean feedbackStatus) {
        this.promotionId = promotionId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.receiveTime = receiveTime;
        this.status = status;
        this.note = note;
        this.reason = reason;
        this.deliveryMethod = deliveryMethod;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.staffId = staffId;
        this.feedbackStatus = feedbackStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public List<OrderDetail> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderDetail> itemList) {
        this.itemList = itemList;
    }

    public List<OrderServices> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<OrderServices> serviceList) {
        this.serviceList = serviceList;
    }

    public List<OrderComboDetail> getComboList() {
        return comboList;
    }

    public void setComboList(List<OrderComboDetail> comboList) {
        this.comboList = comboList;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Boolean getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(Boolean feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }
}
