package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartRestController {

    private CartService cartService;

    @Autowired
    public CartRestController(CartService theCartService){
        cartService = theCartService;
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

    @PostMapping("/carts")
    public Cart AddNewCart(@RequestBody Cart cart){
        cartService.saveCart(cart);
        return cart;
    }

    @PutMapping("/carts")
    public Cart UpdateItemToCart(@RequestBody Cart cart){
        Cart theCart = cartService.getCartById(cart.getId());
        theCart.setCartItems(cart.getCartItems());
        cartService.saveCart(theCart);
        return theCart;
    }

}