package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.CartComboDetailRepository;
import com.tfs.demo.tfs_crud_demo.dao.CustomerRepository;
import com.tfs.demo.tfs_crud_demo.dto.PutPartyToCart;
import com.tfs.demo.tfs_crud_demo.dto.RemoveComboFromCartDTO;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.CartComboDetail;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.entity.Party;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartRestController {

    private final CartService cartService;
    private final PartyService partyService;
    private final CartComboDetailRepository cartComboDetailRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CartRestController(CartService theCartService,
                              CartComboDetailRepository cartComboDetailRepository,
                              CustomerRepository customerRepository,
                              PartyService thePartyService){
        cartService = theCartService;
        this.customerRepository = customerRepository;
        this.cartComboDetailRepository = cartComboDetailRepository;
        partyService = thePartyService;
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