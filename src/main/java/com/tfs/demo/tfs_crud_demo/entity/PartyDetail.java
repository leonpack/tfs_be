package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "partydetail")
public class PartyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int partyDetailId;

    @Column(name = "food_id")
    private int id;

    @Column(name = "food_name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne()
    @JoinColumn(name = "party_id")
    @JsonBackReference
    private Party party;

    public PartyDetail(){

    }
    public PartyDetail(int id, String name, Integer quantity, Party party) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.party = party;
    }
    public int getPartyDetailId() {
        return partyDetailId;
    }

    public void setPartyDetailId(int partyDetailId) {
        this.partyDetailId = partyDetailId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
