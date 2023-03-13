package com.tfs.demo.tfs_crud_demo.dto;

public class PutPartyToCart {

    private Integer cartId;

    private Integer partyId;


    public PutPartyToCart(){

    }

    public PutPartyToCart(Integer cartId, Integer partyId) {
        this.cartId = cartId;
        this.partyId = partyId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }
}
