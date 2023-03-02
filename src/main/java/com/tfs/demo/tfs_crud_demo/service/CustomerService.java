package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> getAllCustomers();

    public Customer getCustomerById(int customerId);

    public Customer getCustomerByEmail(String email);

    public void saveCustomer(Customer theCustomer);

    public void disableCustomer(int customerId);

    public Customer getCustomerByTheAccount(Account theAccount);

//    public boolean checkDuplicateCustomerId(String customerId);

}
