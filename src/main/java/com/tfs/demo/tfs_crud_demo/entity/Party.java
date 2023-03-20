package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String partyName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "note")
    private String note;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "template")
    private String partyTemplate;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PartyDetail> itemList = new ArrayList<>();

    @OneToOne(mappedBy = "party")
    @JsonIgnore
    private Cart cart;

    @OneToOne(mappedBy = "party")
    @JsonIgnore
    private Order order;

    public Party(){

    }

    public Party(String partyName, Integer quantity, String note, Double totalPrice, Double subTotal, String partyTemplate) {
        this.partyName = partyName;
        this.quantity = quantity;
        this.note = note;
        this.totalPrice = totalPrice;
        this.subTotal = subTotal;
        this.partyTemplate = partyTemplate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<PartyDetail> getItemList() {
        return itemList;
    }

    public void setItemList(List<PartyDetail> itemList) {
        this.itemList = itemList;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPartyTemplate() {
        return partyTemplate;
    }

    public void setPartyTemplate(String partyTemplate) {
        this.partyTemplate = partyTemplate;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
