package com.tfs.demo.tfs_crud_demo.dto;

public class RemoveComboFromCartDTO {

    private int cartId;

    private Integer comboId;

    public RemoveComboFromCartDTO(){

    }

    public RemoveComboFromCartDTO(int cartId, Integer comboId) {
        this.cartId = cartId;
        this.comboId = comboId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Integer getComboId() {
        return comboId;
    }

    public void setComboId(Integer comboId) {
        this.comboId = comboId;
    }
}
