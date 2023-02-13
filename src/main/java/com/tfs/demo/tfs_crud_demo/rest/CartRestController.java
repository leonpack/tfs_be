package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.CartDetailRepository;
import com.tfs.demo.tfs_crud_demo.dao.CartRepository;
import com.tfs.demo.tfs_crud_demo.dao.CustomerRepository;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.CartDetail;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartRestController {

    private CartService cartService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CartRestController(CartService theCartService,
                              CartRepository cartRepository,
                              CartDetailRepository cartDetailRepository,
                              CustomerRepository customerRepository){
        cartService = theCartService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/carts")
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @GetMapping("/carts/{cartId}")
    public Cart getCartById(@PathVariable int cartId){
        Cart theCart = cartService.getCartById(cartId);
        return theCart;
    }

//    @PostMapping("/carts")
//    public Cart AddNewCart(@RequestBody Cart cart){
//        cartService.saveCart(cart);
//        return cart;
//    }

    @PutMapping("/carts")
    public Cart UpdateItemToCart(@RequestBody Cart cart){
        Customer theCustomer = customerRepository.getCustomerByCartId(cart.getId());
        if(cart.getCustomer()==null){
            cart.setCustomer(theCustomer);
        }
        cart.setCartItems(cart.getCartItems());
        cartService.saveCart(cart);
        return cart;
    }

}