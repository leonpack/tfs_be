package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.CartRepository;
import com.tfs.demo.tfs_crud_demo.entity.Cart;

import java.util.List;

public interface CartService {

    public List<Cart> getAllCart();

    public Cart getCartById(int cartId);

    public void saveCart(Cart theCart);

    public void deleteCart(int cartId);

}
