package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.CartComboDetailRepository;
import com.tfs.demo.tfs_crud_demo.dao.CustomerRepository;
import com.tfs.demo.tfs_crud_demo.dto.PutPartyToCart;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.entity.Party;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.ComboService;
import com.tfs.demo.tfs_crud_demo.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartRestController {

    private final CartService cartService;
    private final PartyService partyService;
    private final CustomerRepository customerRepository;
    private final ComboService comboService;
    private final CartComboDetailRepository cartComboDetailRepository;

    @Autowired
    public CartRestController(CartService theCartService,
                              ComboService theComboService,
                              CustomerRepository customerRepository,
                              PartyService thePartyService,
                              CartComboDetailRepository cartComboDetailRepository){
        cartService = theCartService;
        this.customerRepository = customerRepository;
        partyService = thePartyService;
        comboService = theComboService;
        this.cartComboDetailRepository = cartComboDetailRepository;
    }

    @GetMapping("/carts")
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @GetMapping("/carts/{cartId}")
    public Cart getCartById(@PathVariable int cartId){
        return cartService.getCartById(cartId);
    }

    @PutMapping("/carts")
    public Cart UpdateItemToCart(@RequestBody Cart cart){
        Customer theCustomer = customerRepository.getCustomerByCartId(cart.getId());
        Cart existCart = cartService.getCartById(cart.getId());
        if(cart.getCustomer()==null){
            cart.setCustomer(theCustomer);
        }
        cart.setComboList(cart.getComboList());
        if(cart.getComboList()==null || cart.getComboList().isEmpty()){
            cart.setComboList(existCart.getComboList());
        }
        if(cart.getParty()==null){
            cart.setParty(existCart.getParty());
        }
        if(cart.getCartItems()==null){
            cart.setCartItems(existCart.getCartItems());
        }
        if(cart.getNumberCart()==null){
            cart.setNumberCart(existCart.getNumberCart());
        }
        if(cart.getTotalPrice()==null){
            cart.setTotalPrice(existCart.getTotalPrice());
        }
        cart.setCartItems(cart.getCartItems());
        cartService.saveCart(cart);
        return cart;
    }


    @PostMapping("/carts/party")
    public ResponseEntity<String> addPartyToCart(@RequestBody PutPartyToCart party){
        Cart cart = cartService.getCartById(party.getCartId());
        Party party1 = partyService.getById(party.getPartyId());
        cart.setParty(party1);
        party1.setCart(cart);
        cartService.saveCart(cart);
        partyService.save(party1);
        return ResponseEntity.ok("Thêm tiệc vào giỏ hàng thành công");
    }

    @PostMapping("/carts/party/new/{cartId}")
    public ResponseEntity<String> addPartyToCartNewVersion(@PathVariable int cartId,@RequestBody Party party){
        Cart cart = cartService.getCartById(cartId);
        partyService.save(party);
        cart.setParty(party);
        party.setCart(cart);
        cartService.saveCart(cart);
        partyService.save(party);
        return ResponseEntity.ok("Thêm tiệc vào giỏ hàng thành công");
    }

    @PostMapping("/carts/removeparty")
    public ResponseEntity<String> removePartyFromCart(@RequestBody PutPartyToCart party){
        Cart cart = cartService.getCartById(party.getCartId());
//        Party party1 = partyService.getById(party.getPartyId());
        cart.setParty(null);
        cartService.saveCart(cart);
        partyService.removeById(party.getPartyId());
        return ResponseEntity.ok("Xoá tiệc khỏi giỏ hàng thành công");
    }

    @GetMapping("/carts/httparty/{cartId}")
    public Party getPartyByCart(@PathVariable int cartId){
        Cart cart = cartService.getCartById(cartId);
        return cart.getParty();
    }

}