package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.CartComboDetailRepository;
import com.tfs.demo.tfs_crud_demo.dao.CustomerRepository;
import com.tfs.demo.tfs_crud_demo.dto.ComboCartDTO;
import com.tfs.demo.tfs_crud_demo.dto.PutPartyToCart;
import com.tfs.demo.tfs_crud_demo.dto.RemoveComboFromCartDTO;
import com.tfs.demo.tfs_crud_demo.entity.*;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.ComboService;
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

//DEPRECATED
//    @PostMapping("/carts/combo/{cartId}")
//    public ResponseEntity<String> addComboToCart(@PathVariable int cartId,@RequestBody CartComboDetail combo){
//        Cart cart = cartService.getCartById(cartId);
//        if(cart.getComboList()==null || cart.getComboList().isEmpty()){
//            cart.setComboList(new ArrayList<>());
//            cartService.saveCart(cart);
//        }
//        for(int i = 0; i < cart.getComboList().size();i++){
//            if(cart.getComboList().get(i).getComboId().toString().equals(combo.getComboId().toString())){
//                cart.getComboList().get(i).setComboQuantity(cart.getComboList().get(i).getComboQuantity()+1);
//                cart.getComboList().get(i).setSubtotal(cart.getComboList().get(i).getComboQuantity()*cart.getComboList().get(i).getComboPrice());
//                cartService.saveCart(cart);
//                return ResponseEntity.ok("Đã update số lượng combo thành công");
//            }
//        }
//        cart.addCombo(combo);
//        cartService.saveCart(cart);
//        return ResponseEntity.ok("Thêm combo vào giỏ hàng thành công!");
//    }
//
//    @PostMapping("/carts/removeCombo")
//    public ResponseEntity<String> removeCombo(@RequestBody RemoveComboFromCartDTO removeComboFromCartDTO){
//        Cart cart = cartService.getCartById(removeComboFromCartDTO.getCartId());
//        for(CartComboDetail item: cart.getComboList()){
//            if(item.getComboId().toString().equals(removeComboFromCartDTO.getComboId().toString())&&item.getComboQuantity()>1){
//                item.setComboQuantity(item.getComboQuantity()-1);
//                cartService.saveCart(cart);
//                return ResponseEntity.ok("Giảm số lượng cart thành công");
//            } else if(item.getComboId().toString().equals(removeComboFromCartDTO.getComboId().toString())&&item.getComboQuantity()==1){
//                item.setComboCart(null);
//                cartComboDetailRepository.deleteById(removeComboFromCartDTO.getComboId());
//                return ResponseEntity.ok("Xoá combo ra khỏi cart thành công");
//            }
//        }
//        return ResponseEntity.ok("This place is forbidden");
//    }
    //NEW COMBO
    @PostMapping("/carts/addcombo")
    public ResponseEntity<String> addComboToCart(@RequestBody ComboCartDTO comboDTO){
        Cart cart = cartService.getCartById(comboDTO.getCartId());
        Combo combo = comboService.getComboById(comboDTO.getComboId());
        if(cart.getComboList().isEmpty()){
            Double total = combo.getComboPrice()* comboDTO.getQuantity();
            CartComboDetail comboDetail = cartComboDetailRepository.save(new CartComboDetail(combo.getId(), combo.getComboName(), combo.getComboPrice(), combo.getImage(), comboDTO.getQuantity(), total));
            cart.getComboList().add(comboDetail);
            cart.setNumberCart(cart.getNumberCart()+ comboDTO.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice()+total);
            cartService.saveCart(cart);
            return ResponseEntity.ok("Add combo success");
        } else if(!cart.getComboList().isEmpty()){
            for(CartComboDetail item: cart.getComboList()){
                if(item.getComboId().equals(comboDTO.getComboId())){
                    item.setComboQuantity(item.getComboQuantity()+ comboDTO.getQuantity());
                    item.setSubtotal(item.getSubtotal()+(item.getComboPrice()*comboDTO.getQuantity()));
                    cartComboDetailRepository.save(item);
                    cartService.saveCart(cart);
                }
            }
            //if the combo not in the cart yet then add new one
            Double total = combo.getComboPrice()*comboDTO.getQuantity();
            CartComboDetail comboDetail = new CartComboDetail(combo.getId(), combo.getComboName(), combo.getComboPrice(), combo.getImage(), comboDTO.getQuantity(), total);
            cartComboDetailRepository.save(comboDetail);
            cart.getComboList().add(comboDetail);
            cartService.saveCart(cart);
            return ResponseEntity.ok("update combo success");
        }
        return ResponseEntity.ok("Forbidden zone");
    }

    @PostMapping("/carts/updatecombo")
    public ResponseEntity<String> updateComboInCart(@RequestBody ComboCartDTO comboDTO){
        Cart cart = cartService.getCartById(comboDTO.getCartId());
        Combo combo = comboService.getComboById(comboDTO.getComboId());
        for(CartComboDetail item: cart.getComboList()){
            if(item.getComboId().equals(combo.getId())){
                int previousQuantity = item.getComboQuantity();
                Double previousSubTotal = item.getSubtotal();
                item.setComboQuantity(item.getComboQuantity()+ comboDTO.getQuantity());
                if(item.getComboQuantity()<=0){
                    cart.setNumberCart(cart.getNumberCart()-previousQuantity);
                    cart.setTotalPrice(cart.getTotalPrice()-previousSubTotal);
                    cartComboDetailRepository.delete(item);
                    cartService.saveCart(cart);
                    return ResponseEntity.ok("Update combo in cart done");
                }
                Double newSubtotal = item.getComboPrice()*item.getComboQuantity();
                cart.setNumberCart(cart.getNumberCart()+item.getComboQuantity());
                cart.setTotalPrice(cart.getTotalPrice()+newSubtotal);
                cartService.saveCart(cart);
                return ResponseEntity.ok("Update combo in cart done");
            }
        }
        return ResponseEntity.ok("Forbidden Zone");
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