package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orderdetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "food_id")
    private Integer food_id;

    @Column(name = "name")
    private String name;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sub_total")
    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order theOrder;

    public OrderDetail(){

    }

    public OrderDetail(int id, Integer food_id, String name, Double currentPrice, Integer quantity, Double subTotal, Order theOrder) {
        this.id = id;
        this.food_id = food_id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.theOrder = theOrder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Order getTheOrder() {
        return theOrder;
    }

    public void setTheOrder(Order theOrder) {
        this.theOrder = theOrder;
    }
}
