package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "combo")
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "combo_name")
    private String comboName;

    @Column(name = "combo_price")
    private Double comboPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "combo", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<ComboDetail> comboItems = new ArrayList<>();

    @Column(name = "status")
    private Boolean status;

    public Combo(){

    }

    public Combo(String comboName, Double comboPrice, String description, String image, List<ComboDetail> comboItems, Boolean status) {
        this.comboName = comboName;
        this.comboPrice = comboPrice;
        this.description = description;
        this.image = image;
        this.comboItems = comboItems;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ComboDetail> getComboItems() {
        return comboItems;
    }

    public void setComboItems(List<ComboDetail> comboItems) {
        this.comboItems = comboItems;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
