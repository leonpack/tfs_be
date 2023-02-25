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
    private Integer id;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "party_description")
    private String partyDescription;

    @Column(name = "party_image")
    private String partyImage;

    @Column(name = "party_price")
    private Double partyPrice;

    @OneToMany(mappedBy = "party", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},orphanRemoval = true)
    @JsonManagedReference
    private List<PartyDetail> partyItems = new ArrayList<>();

    @Column(name = "status")
    private Boolean status;

    public Party(){

    }

    public Party(String partyName, String partyDescription, String partyImage, Double partyPrice, List<PartyDetail> partyItems, Boolean status) {
        this.partyName = partyName;
        this.partyDescription = partyDescription;
        this.partyImage = partyImage;
        this.partyPrice = partyPrice;
        this.partyItems = partyItems;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyDescription() {
        return partyDescription;
    }

    public void setPartyDescription(String partyDescription) {
        this.partyDescription = partyDescription;
    }

    public String getPartyImage() {
        return partyImage;
    }

    public void setPartyImage(String partyImage) {
        this.partyImage = partyImage;
    }

    public Double getPartyPrice() {
        return partyPrice;
    }

    public void setPartyPrice(Double partyPrice) {
        this.partyPrice = partyPrice;
    }

    public List<PartyDetail> getPartyItems() {
        return partyItems;
    }

    public void setPartyItems(List<PartyDetail> partyItems) {
        this.partyItems = partyItems;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
