package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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
    private Date orderDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "received_time")
    private Date receiveTime;

    @Column(name = "activity_status")
    private String status;

    @JoinColumn(name = "customer_id")
    private Integer customerId;

    @JoinColumn(name = "restaurant_id")
    private String restaurantId;

    @JoinColumn(name = "staff_id")
    private String staffId;

    @OneToMany(mappedBy = "theOrder",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonManagedReference
    private List<OrderDetail> itemList = new ArrayList<>();

    public Order(){

    }

    public Order(Double totalPrice, Integer totalQuantity, String paymentMethod, String deliveryAddress, Date orderDate, Date deliveryDate, Date receiveTime, String status, Integer customerId, String restaurantId, String staffId, List<OrderDetail> itemList) {
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.receiveTime = receiveTime;
        this.status = status;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.staffId = staffId;
        this.itemList = itemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public List<OrderDetail> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderDetail> itemList) {
        this.itemList = itemList;
    }
}
