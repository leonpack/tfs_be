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
    private int order_item_id;

    @Column(name = "food_id")
    private Integer id;

    @Column(name = "combo_id")
    private Integer comboId;

    @Column(name = "party_id")
    private Integer partyId;

    @Column(name = "name")
    private String name;

    @Column(name = "current_price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "image_url")
    private String image;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order theOrder;

    public OrderDetail(){

    }

    public OrderDetail(Integer id, Integer comboId, Integer partyId, String name, Double price, Integer quantity, Double subTotal, String image) {
        this.id = id;
        this.comboId = comboId;
        this.partyId = partyId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.image = image;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComboId() {
        return comboId;
    }

    public void setComboId(Integer comboId) {
        this.comboId = comboId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Order getTheOrder() {
        return theOrder;
    }

    public void setTheOrder(Order theOrder) {
        this.theOrder = theOrder;
    }
}
