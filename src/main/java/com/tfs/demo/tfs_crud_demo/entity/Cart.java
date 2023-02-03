package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int id;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cart-detail")
    private Set<CartDetail> cartItems = new HashSet<>();

    public Cart(){

    }

    public Cart(Double totalPrice, Customer customer) {
        this.totalPrice = totalPrice;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<CartDetail> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartDetail> cartItems) {
        this.cartItems = cartItems;
    }

    public void add(CartDetail item){
        if(cartItems == null){
            cartItems = new HashSet<>();
        }
        cartItems.add(item);
        item.setCart(this);
    }

}