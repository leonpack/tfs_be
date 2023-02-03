package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cartdetail")
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "food_id")
    private int foodId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "current_price")
    private Double current_price;

    @Column(name = "sub_total")
    private Double subTotal;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cart_id")
    @JsonBackReference(value = "cart-detail")
    private Cart cart;

    public CartDetail(){

    }

    public CartDetail(int foodId, int quantity, Double current_price, Double subTotal) {
        this.foodId = foodId;
        this.quantity = quantity;
        this.current_price = current_price;
        this.subTotal = subTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
