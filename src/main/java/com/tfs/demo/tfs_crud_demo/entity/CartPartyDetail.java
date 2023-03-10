package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_party_detail")
public class CartPartyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "party_id")
    private Integer partyId;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "party_total_price")
    private Double partyTotalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cartParty;

    public CartPartyDetail(){

    }

    public CartPartyDetail(Integer partyId, String partyName, Double partyTotalPrice) {
        this.partyId = partyId;
        this.partyName = partyName;
        this.partyTotalPrice = partyTotalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Double getPartyTotalPrice() {
        return partyTotalPrice;
    }

    public void setPartyTotalPrice(Double partyTotalPrice) {
        this.partyTotalPrice = partyTotalPrice;
    }

    public Cart getCartParty() {
        return cartParty;
    }

    public void setCartParty(Cart cartParty) {
        this.cartParty = cartParty;
    }
}
