package com.tfs.demo.tfs_crud_demo.entity;

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

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "note")
    private String note;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "template")
    private String partyTemplate;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PartyDetail> itemList = new ArrayList<>();

    @OneToOne(mappedBy = "party")
    private Cart cart;

    @OneToOne(mappedBy = "party")
    private Order order;

    public Party(){

    }

    public Party(Integer quantity, String note, Double totalPrice, String partyTemplate) {
        this.quantity = quantity;
        this.note = note;
        this.totalPrice = totalPrice;
        this.partyTemplate = partyTemplate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
