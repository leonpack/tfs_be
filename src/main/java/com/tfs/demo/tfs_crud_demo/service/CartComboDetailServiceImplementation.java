package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.CartComboDetailRepository;
import com.tfs.demo.tfs_crud_demo.entity.CartComboDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartComboDetailServiceImplementation implements CartComboDetailService{

    private final CartComboDetailRepository cartComboDetailRepository;

    @Autowired
    public CartComboDetailServiceImplementation(CartComboDetailRepository theCartComboDetailRepository){
        cartComboDetailRepository = theCartComboDetailRepository;
    }


    @Override
    public void deleteComboInCart(int comboId) {
        Optional<CartComboDetail> result = cartComboDetailRepository.findById(comboId);
        CartComboDetail combo = null;
        if(result.isPresent()){
            combo = result.get();
            cartComboDetailRepository.delete(combo);
        } else {
            throw new RuntimeException("This combo not found!");
        }
    }
}
