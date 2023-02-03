package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.CartRepository;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImplementation implements CartService{

    private CartRepository cartRepository;

    @Autowired
    public CartServiceImplementation(CartRepository theCartRepository){
        cartRepository = theCartRepository;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(int id) {
        Optional<Cart> result = cartRepository.findById(id);
        Cart theCart = null;
        if(result.isPresent()){
            theCart = result.get();
        } else {
            throw new RuntimeException("Cart with id " +id + " not found!");
        }
        return theCart;
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

}