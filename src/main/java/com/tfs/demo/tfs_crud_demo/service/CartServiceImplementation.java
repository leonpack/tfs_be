package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.CartRepository;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImplementation implements CartService{

    private CartRepository cartRepository;
    private EntityManager entityManager;

    @Autowired
    public CartServiceImplementation(CartRepository theCartRepository, EntityManager theEntityManager){
        cartRepository = theCartRepository;
        entityManager = theEntityManager;
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(int cartId) {
        Optional<Cart> resutl = cartRepository.findById(cartId);
        Cart theCart = null;
        if(resutl.isPresent()){
            theCart = resutl.get();
        } else {
            throw new RuntimeException("Cart not found!");
        }
        return theCart;
    }

    @Override
    public void saveCart(Cart theCart) {
        cartRepository.save(theCart);
    }

    @Override
    public void deleteCart(int cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public boolean checkDuplicateCustomerId(String customerId) {
        Query theQuery = entityManager.createQuery("select theCustomerCart.customerId from Cart");
        for(int i = 0; i < theQuery.getResultList().size();i++){
            if(customerId.equals(theQuery.getResultList().get(i))){
                throw new RuntimeException("Duplicate customerID has been found, please try again");
            }
        }
        return true;
    }

}
