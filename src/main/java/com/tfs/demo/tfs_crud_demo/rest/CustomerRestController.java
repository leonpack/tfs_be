package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CustomerRestController {
    private CustomerService customerService;
    private AccountService accountService;


    private CartService cartService;

    @Autowired
    public CustomerRestController(CustomerService theCustomerService, AccountService theAccountService, CartService theCartService){
        customerService = theCustomerService;
        accountService = theAccountService;
        cartService = theCartService;
    }

    @GetMapping("/customers")
    public List<Customer> getALlCustomers(){
        return customerService.getAllCustomers();
    }

//    @GetMapping("/customers/{customerId}")
//    @ApiOperation("Return customer based on customerId")
//    public Customer getCustomerById(@PathVariable int customerId){
//        return customerService.getCustomerById(customerId);
//    }

    @GetMapping("/customers/{accountId}")
    public Customer getCustomerByAccountId(@PathVariable String accountId){
        Customer theCustomer = customerService.getCustomerByTheAccount(accountService.getAccountById(accountId));
        return theCustomer;
    }

    @GetMapping("/customers/cart/{accountId}")
    public Cart getCustomerCart(@PathVariable String accountId){
        Customer theCustomer = customerService.getCustomerByTheAccount(accountService.getAccountById(accountId));
        Cart theCart = theCustomer.getCart();
        return theCart;
    }

    @PostMapping("/customers/{accountId}")
    public String addNewCustomer(@RequestBody Customer theCustomer,@PathVariable String accountId){
//        if(!customerService.checkDuplicateCustomerId(theCustomer.getCustomerId())){
//            return "Duplicate customer with id " +theCustomer.getCustomerId() + " has been found, please try again";
//        }
        Account theAccount = accountService.getAccountById(accountId);
        theCustomer.setTheAccount(theAccount);
        theCustomer.setCustomerId(0);
        customerService.saveCustomer(theCustomer);
        Cart theCart = new Cart((double) 0, 0, theCustomer);
        cartService.saveCart(theCart);
//        Cart theCart = new Cart(theCustomer);
//        cartService.saveCart(theCart);
        return "Saved " +theCustomer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer){
        Customer customer = customerService.getCustomerById(theCustomer.getCustomerId());
        if(theCustomer.getTheAccount()==null){
            theCustomer.setTheAccount(customer.getTheAccount());
        }
        if(theCustomer.getCart()==null){
            theCustomer.setCart(customer.getCart());
        }
        if(theCustomer.getCustomerName()==null){
            theCustomer.setCustomerName(customer.getCustomerName());
        }
        if(theCustomer.getAddress()==null){
            theCustomer.setAddress(customer.getAddress());
        }
        if(theCustomer.getEmail()==null){
            theCustomer.setEmail(customer.getEmail());
        }
        if(theCustomer.getAvatarURL()==null){
            theCustomer.setAvatarURL(customer.getAvatarURL());
        }
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String disableCustomer(@PathVariable int customerId){
        customerService.disableCustomer(customerId);
        return "Disable customer with id " +customerId + " completed";
    }

}
