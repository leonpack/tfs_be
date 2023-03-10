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
    @JsonManagedReference()
    @OrderBy(value = "cart_item_id desc")
    private List<CartDetail> cartItems = new ArrayList<>();


    @OneToMany(mappedBy = "comboCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy(value = "id desc")
    private List<CartComboDetail> comboList = new ArrayList<>();


    @OneToMany(mappedBy = "cartParty", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartPartyDetail> partyItem = new ArrayList<>();

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

    public List<CartPartyDetail> getPartyItem() {
        return partyItem;
    }

    public void setPartyItem(List<CartPartyDetail> partyItem) {
        this.partyItem = partyItem;
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

    public void addParty(CartPartyDetail item){
        if(partyItem == null){
            partyItem = new ArrayList<>();
        }
        partyItem.add(item);
        item.setCartParty(this);
    }
}