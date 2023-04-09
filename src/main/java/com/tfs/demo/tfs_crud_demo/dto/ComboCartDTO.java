package com.tfs.demo.tfs_crud_demo.dto;

public class ComboCartDTO {

    private int comboId;
    private int cartId;
    private int quantity;

    public ComboCartDTO(){

    }

    public ComboCartDTO(int comboId, int cartId, int quantity) {
        this.comboId = comboId;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
