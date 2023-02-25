package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "combodetail")
public class ComboDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int comboDetailId;

    @Column(name = "food_id")
    private int id;

    @Column(name = "food_name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "combo_id")
    @JsonBackReference
    private Combo combo;

    public ComboDetail(){

    }

    public ComboDetail(int id, String name, Combo combo) {
        this.id = id;
        this.name = name;
        this.combo = combo;
    }

    public int getComboDetailId() {
        return comboDetailId;
    }

    public void setComboDetailId(int comboDetailId) {
        this.comboDetailId = comboDetailId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }
}
