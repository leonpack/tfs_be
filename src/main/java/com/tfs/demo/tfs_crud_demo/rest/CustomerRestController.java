package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    private CustomerService customerService;
    private AccountService accountService;

    @Autowired
    public CustomerRestController(CustomerService theCustomerService, AccountService theAccountService){
        customerService = theCustomerService;
        accountService = theAccountService;
    }

    @GetMapping("/customers")
    public List<Customer> getALlCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/customers/{accountId}")
    public String addNewCustomer(@RequestBody Customer theCustomer,@PathVariable String accountId){
        if(!customerService.checkDuplicateCustomerId(theCustomer.getCustomerId())){
            return "Duplicate customer with id " +theCustomer.getCustomerId() + " has been found, please try again";
        }
        Account theAccount = accountService.getAccountById(accountId);
        theCustomer.setTheAccount(theAccount);
        customerService.saveCustomer(theCustomer);
        return "Saved " +theCustomer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer){
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String disableCustomer(@PathVariable String customerId){
        customerService.disableCustomer(customerId);
        return "Disable customer with id " +customerId + " completed";
    }

}
