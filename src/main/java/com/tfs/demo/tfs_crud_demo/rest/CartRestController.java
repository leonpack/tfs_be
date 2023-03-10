package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.*;
import com.tfs.demo.tfs_crud_demo.dto.RemoveComboFromCartDTO;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.CartComboDetail;
import com.tfs.demo.tfs_crud_demo.entity.CartPartyDetail;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartRestController {

    private CartService cartService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    private final CartComboDetailRepository cartComboDetailRepository;

    private final CustomerRepository customerRepository;
    private final PartyRepository partyRepository;

    @Autowired
    public CartRestController(CartService theCartService,
                              CartRepository cartRepository,
                              CartDetailRepository cartDetailRepository,
                              CartComboDetailRepository cartComboDetailRepository,
                              CustomerRepository customerRepository,
                              PartyRepository partyRepository){
        cartService = theCartService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.customerRepository = customerRepository;
        this.cartComboDetailRepository = cartComboDetailRepository;
        this.partyRepository = partyRepository;
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

    @PostMapping("/carts/combo/{cartId}")
    public ResponseEntity<String> addComboToCart(@PathVariable int cartId,@RequestBody CartComboDetail combo){
        Cart cart = cartService.getCartById(cartId);
        if(cart.getComboList()==null || cart.getComboList().isEmpty()){
            cart.setComboList(new ArrayList<>());
            cartService.saveCart(cart);
        }
        for(int i = 0; i < cart.getComboList().size();i++){
            if(cart.getComboList().get(i).getComboId().toString().equals(combo.getComboId().toString())){
                cart.getComboList().get(i).setComboQuantity(cart.getComboList().get(i).getComboQuantity()+1);
                cart.getComboList().get(i).setSubtotal(cart.getComboList().get(i).getComboQuantity()*cart.getComboList().get(i).getComboPrice());
                cartService.saveCart(cart);
                return ResponseEntity.ok("Đã update số lượng combo thành công");
            }
        }
        cart.addCombo(combo);
        cartService.saveCart(cart);
        return ResponseEntity.ok("Thêm combo vào giỏ hàng thành công!");
    }

    @PostMapping("/carts/removeCombo")
    public ResponseEntity<String> removeCombo(@RequestBody RemoveComboFromCartDTO removeComboFromCartDTO){
        Cart cart = cartService.getCartById(removeComboFromCartDTO.getCartId());
        for(CartComboDetail item: cart.getComboList()){
            if(item.getComboId().toString().equals(removeComboFromCartDTO.getComboId().toString())&&item.getComboQuantity()>1){
                item.setComboQuantity(item.getComboQuantity()-1);
                cartService.saveCart(cart);
                return ResponseEntity.ok("Giảm số lượng cart thành công");
            } else if(item.getComboId().toString().equals(removeComboFromCartDTO.getComboId().toString())&&item.getComboQuantity()==1){
                item.setComboCart(null);
                cartComboDetailRepository.deleteById(removeComboFromCartDTO.getComboId());
                return ResponseEntity.ok("Xoá combo ra khỏi cart thành công");
            }
        }
        return ResponseEntity.ok("This place is forbidden");
    }

    @PostMapping("/carts/party/{cartId}")
    public ResponseEntity<String> addPartyToCart(@PathVariable int cartId, @RequestBody CartPartyDetail party){
        Cart cart = cartService.getCartById(cartId);
        if(cart.getPartyItem()==null || cart.getPartyItem().isEmpty()){
            cart.setPartyItem(new ArrayList<>());
        }
        if(cart.getPartyItem().size()>=1){
            throw new RuntimeException("This cart can only have 1 party item");
        } else if (cart.getPartyItem().size()==0){
            cart.addParty(party);
            party.setCartParty(cart);
            cartService.saveCart(cart);
            return ResponseEntity.ok("Thêm tiệc vào giỏ hàng thành công");
        }
        return ResponseEntity.ok("This place is forbidden");
    }

    @PostMapping("/carts/removeParty/{cartId}")
    public ResponseEntity<String> removePartyFromCart(@PathVariable int cartId){
        Cart cart = cartService.getCartById(cartId);
        cart.getPartyItem().remove(0);
        cart.setPartyItem(null);
        cartService.saveCart(cart);
        return ResponseEntity.ok("Xoá tiệc thành công");
    }

}