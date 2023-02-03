package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Cart;

import java.util.List;

public interface CartService {

    public List<Cart> getAllCarts();

    public Cart getCartById(int id);

    public void saveCart(Cart cart);

}