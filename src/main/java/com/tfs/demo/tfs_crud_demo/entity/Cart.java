package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int id;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_quantity")
    private Integer numberCart;

    @OneToOne()
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy(value = "cart_item_id desc")
    private List<CartDetail> cartItems = new ArrayList<>();


    @OneToMany(mappedBy = "comboCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy(value = "id desc")
    private List<CartComboDetail> comboList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "party_id")
    private Party party;

    public Cart(){

    }

    public Cart(Double totalPrice, Integer numberCart, Customer customer) {
        this.totalPrice = totalPrice;
        this.numberCart = numberCart;
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

    public Integer getNumberCart() {
        return numberCart;
    }

    public void setNumberCart(Integer numberCart) {
        this.numberCart = numberCart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartDetail> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartDetail> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartComboDetail> getComboList() {
        return comboList;
    }

    public void setComboList(List<CartComboDetail> comboList) {
        this.comboList = comboList;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public void add(CartDetail item){
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        item.setCart(this);
    }

    public void addCombo(CartComboDetail item){
        if(comboList == null){
            comboList = new ArrayList<>();
        }
        comboList.add(item);
        item.setComboCart(this);
    }

}