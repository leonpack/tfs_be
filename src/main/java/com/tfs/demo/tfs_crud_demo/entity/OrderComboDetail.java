package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "order_combo_detail")
public class OrderComboDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "combo_id")
    private Integer comboId;

    @Column(name = "combo_name")
    private String comboName;

    @Column(name = "combo_price")
    private Double comboPrice;

    @Column(name = "combo_quantity")
    private Integer comboQuantity;

    @Column(name = "combo_sub_total")
    private Double subTotal;

    @Column(name = "combo_image")
    private String comboImage;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order orderCombo;

    public OrderComboDetail(){

    }

    public OrderComboDetail(Integer comboId, String comboName, Double comboPrice, Integer comboQuantity, Double subTotal, String comboImage) {
        this.comboId = comboId;
        this.comboName = comboName;
        this.comboPrice = comboPrice;
        this.comboQuantity = comboQuantity;
        this.subTotal = subTotal;
        this.comboImage = comboImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getComboId() {
        return comboId;
    }

    public void setComboId(Integer comboId) {
        this.comboId = comboId;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public Double getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(Double comboPrice) {
        this.comboPrice = comboPrice;
    }

    public Integer getComboQuantity() {
        return comboQuantity;
    }

    public void setComboQuantity(Integer comboQuantity) {
        this.comboQuantity = comboQuantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getComboImage() {
        return comboImage;
    }

    public void setComboImage(String comboImage) {
        this.comboImage = comboImage;
    }

    public Order getOrderCombo() {
        return orderCombo;
    }

    public void setOrderCombo(Order orderCombo) {
        this.orderCombo = orderCombo;
    }
}
