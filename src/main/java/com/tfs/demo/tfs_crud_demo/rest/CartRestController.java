package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api")
public class CartRestController {
//
//    private CartService cartService;
//    private CustomerService customerService;
//    private FoodService foodService;
//
//
//    public CartRestController(CartService theCartService, CustomerService theCustomerService, FoodService theFoodService){
//        cartService = theCartService;
//        customerService = theCustomerService;
//        foodService = theFoodService;
//    }
//
//    @GetMapping("/carts")
//    public List<Cart> getAllCarts(){
//        return cartService.getAllCart();
//    }
//
//    @GetMapping("/carts/{cartId}")
//    public Cart getCartById(@PathVariable int cartId){
//        Cart theCart = cartService.getCartById(cartId);
//        return theCart;
//    }
//
//    @PostMapping("/carts/{customerId}")
//    public String addCartToCustomer(@PathVariable int customerId){
//        Customer theCustomer = customerService.getCustomerById(customerId);
//        if(theCustomer==null){
//            throw new RuntimeException("Customer with id " +customerId + " not found, please try again!");
//        }
//        if(!cartService.checkDuplicateCustomerId(customerId)){
//            return "Duplicate customerId";
//        }
//        Cart theCart = new Cart(theCustomer);
//        cartService.saveCart(theCart);
//        return "Add customer " +theCustomer + " to cart " +theCart.getCartId()+" successful";
//    }
//
//    @DeleteMapping("/carts/{cartId}")
//    public String deleteCart(@PathVariable int cartId){
//        //get theCart
//        Cart theCart = cartService.getCartById(cartId);
//        //disconnect relationship between cart and customer by set customer cart to null
//        theCart.getTheCustomerCart().setTheCart(null);
//        //after that we can delete cart
//        cartService.deleteCart(cartId);
//        return "Delete cart " +cartId + " successful!";
//    }
//
//    //this function is still WIP
//    @PostMapping("/addToCart/{foodId}TO{cartId}")
//    public String addFoodToCart(@PathVariable int foodId, @PathVariable int cartId){
//        Food theFood = foodService.getFoodById(foodId);
//        if(theFood == null){
//            throw new RuntimeException("Food not found");
//        }
//        Cart theCart = cartService.getCartById(cartId);
//        if(theCart == null){
//            throw new RuntimeException("Cart not found");
//        }
//        theCart.addFood(theFood);
//        theFood.addCart(theCart);
//
//        foodService.saveFood(theFood);
//        cartService.saveCart(theCart);
//        return "added";
//    }
//
//    @DeleteMapping("/deleteFromCart/{foodId}TO{cartId}")
//    public String deleteFromCart(@PathVariable int foodId, @PathVariable int cartId){
//        Food theFood = foodService.getFoodById(foodId);
//        if(theFood == null){
//            throw new RuntimeException("Food not found");
//        }
//        Cart theCart = cartService.getCartById(cartId);
//        if(theCart == null){
//            throw new RuntimeException("Cart not found");
//        }
//        theCart.removeFood(theFood);
//        cartService.saveCart(theCart);
//        return "removed";
//    }
}
