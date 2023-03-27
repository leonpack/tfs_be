package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Cart;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CustomerRestController {
    private CustomerService customerService;
    private AccountService accountService;
    private StaffService staffService;
    private CartService cartService;

    @Autowired
    public CustomerRestController(CustomerService theCustomerService, AccountService theAccountService, CartService theCartService, StaffService theStaffService){
        customerService = theCustomerService;
        accountService = theAccountService;
        cartService = theCartService;
        staffService = theStaffService;
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

    @GetMapping("/customers/byid/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId){
        return customerService.getCustomerById(customerId);
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
        //check duplicate email when adding new customer
//        if(customerService.getCustomerByEmail(theCustomer.getEmail()).getCustomerId()!=theCustomer.getCustomerId()
//        && staffService.getStaffByEmail(theCustomer.getEmail()).getTheAccountForStaff().getAccountId()!= theCustomer.getTheAccount().getAccountId()){
//            throw new RuntimeException("This email has been linked with another account, please try again");
//        }
        customerService.saveCustomer(theCustomer);
        Cart theCart = new Cart((double) 0, 0, theCustomer);
        cartService.saveCart(theCart);
//        Cart theCart = new Cart(theCustomer);
//        cartService.saveCart(theCart);
        return "Saved " +theCustomer;
    }

    @PostMapping("/customers")
    public Customer registerAccountForCustomer(@RequestBody Customer customer){
        if(customer.getAvatarURL().isBlank() || customer.getAvatarURL()==null){
            customer.setAvatarURL("https://live.staticflickr.com/65535/52719475105_ec5b21e417_w.jpg");
        }
        customerService.saveCustomer(customer);
        Cart cart = new Cart((double) 0, 0, customer);
        return customer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer){
        Customer customer = customerService.getCustomerById(theCustomer.getCustomerId());

        if(theCustomer.getTheAccount()!=null && theCustomer.getTheAccount().getPhoneNumber()!=null && !theCustomer.getTheAccount().getPhoneNumber().equals(customer.getTheAccount().getPhoneNumber())){
            Account accountCheck = accountService.checkLoginByPhone(theCustomer.getTheAccount().getPhoneNumber());
            if(accountCheck==null){
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
                if(theCustomer.getTheAccount()==null){
                    theCustomer.setTheAccount(customer.getTheAccount());
                }
                accountService.saveAccount(theCustomer.getTheAccount());
                customerService.saveCustomer(theCustomer);
                return theCustomer;
            } else if (accountCheck!=null || !accountCheck.getAccountId().equals(theCustomer.getTheAccount().getAccountId())){
                throw new RuntimeException("This phone number is already linked with another account!");
            }
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
        if(theCustomer.getTheAccount()==null){
            theCustomer.setTheAccount(customer.getTheAccount());
        }
        //TODO check duplicate email & phoneNumber
//        if(customerService.getCustomerByEmail(theCustomer.getEmail()).getCustomerId()!=theCustomer.getCustomerId()
//                && staffService.getStaffByEmail(theCustomer.getEmail()).getTheAccountForStaff().getAccountId()!= theCustomer.getTheAccount().getAccountId()){
//            throw new RuntimeException("This email has been linked with another account, please try again");
//        }
//        if(theCustomer.getTheAccount().getPhoneNumber()!=null){
//            Account accountCheck = accountService.checkLoginByPhone(theCustomer.getTheAccount().getPhoneNumber());
//            if(accountCheck!=null || !accountCheck.getAccountId().equals(theCustomer.getTheAccount().getAccountId())){
//                throw new RuntimeException("This phone number already linked with another account!");
//            }
//        }
        accountService.saveAccount(theCustomer.getTheAccount());
        customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String disableCustomer(@PathVariable int customerId){
        customerService.disableCustomer(customerId);
        return "Disable customer with id " +customerId + " completed";
    }

    @GetMapping("/customers/checkByPhoneNumber/{phoneNumber}")
    public Customer checkCustomerByPhoneNumber(@PathVariable String phoneNumber){
        Account account = accountService.checkLoginByPhone(phoneNumber);
        if(account!=null && !account.getRoleId().toString().equals("5")){
            throw new RuntimeException("This phone number is belong to one of the staff of TFS");
        }
        if(account!=null && account.getRoleId().toString().equals("5")){
            Customer customer = customerService.getCustomerByTheAccount(account);
            if(customer==null){
                throw new RuntimeException("This phone number is not link with any account");
            }
            else
                return customer;
        }
        else
            return null;
    }

}
