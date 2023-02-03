package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Return list of all customers")
    public List<Customer> getALlCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    @ApiOperation("Return customer based on customerId")
    public Customer getCustomerById(@PathVariable int customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/customers/{accountId}")
    @ApiOperation("Add new Customer (Need to create Account first, " +
            "then put accountId from that created Account to PathVariable {accountId} to link between account and customer)" +
            "(need full Customer's Json and accountID(this accountId must not linked with any other specific role such as other staff, other customer, etc...))")
    public String addNewCustomer(@RequestBody Customer theCustomer,@PathVariable String accountId){
//        if(!customerService.checkDuplicateCustomerId(theCustomer.getCustomerId())){
//            return "Duplicate customer with id " +theCustomer.getCustomerId() + " has been found, please try again";
//        }
        Account theAccount = accountService.getAccountById(accountId);
        theCustomer.setTheAccount(theAccount);
        theCustomer.setCustomerId(0);
        customerService.saveCustomer(theCustomer);
        Cart theCart = new Cart((double) 0, theCustomer);
        cartService.saveCart(theCart);
//        Cart theCart = new Cart(theCustomer);
//        cartService.saveCart(theCart);
        return "Saved " +theCustomer;
    }

    @PutMapping("/customers")
    @ApiOperation("Edit existing account(need full Customer's JSON)")
    public Customer updateCustomer(@RequestBody Customer theCustomer){
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    @ApiOperation("Disable existing customer based on customerId")
    public String disableCustomer(@PathVariable int customerId){
        customerService.disableCustomer(customerId);
        return "Disable customer with id " +customerId + " completed";
    }

    @PostMapping("/customers/{customerId}SET{imageURL}")
    public String updateCustomerAvatar(@PathVariable int customerId, @PathVariable String imageURL){
        Customer theCustomer = customerService.getCustomerById(customerId);
        theCustomer.setAvatarURL(imageURL);
        return "Update customer " +theCustomer +" avatar successfully!";
    }

}
