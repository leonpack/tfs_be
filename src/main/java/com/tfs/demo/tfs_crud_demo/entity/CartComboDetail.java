package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "cart_combo_detail")
public class CartComboDetail {

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

    @Column(name = "combo_image")
    private String comboImage;

    @Column(name = "combo_quantity")
    private Integer comboQuantity;

    @Column(name = "combo_sub_total")
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart comboCart;

    public CartComboDetail(){

    }

    public CartComboDetail(Integer comboId, String comboName, Double comboPrice, String comboImage, Integer comboQuantity, Double subtotal) {
        this.comboId = comboId;
        this.comboName = comboName;
        this.comboPrice = comboPrice;
        this.comboImage = comboImage;
        this.comboQuantity = comboQuantity;
        this.subtotal = subtotal;
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

    public String getComboImage() {
        return comboImage;
    }

    public void setComboImage(String comboImage) {
        this.comboImage = comboImage;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getComboQuantity() {
        return comboQuantity;
    }

    public void setComboQuantity(Integer comboQuantity) {
        this.comboQuantity = comboQuantity;
    }

    public Cart getComboCart() {
        return comboCart;
    }

    public void setComboCart(Cart comboCart) {
        this.comboCart = comboCart;
    }
}
