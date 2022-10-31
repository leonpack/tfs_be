package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.CustomerRepository;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService{

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImplementation(CustomerRepository theCustomerRepository){
        customerRepository = theCustomerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String customerId) {
        Optional<Customer> result = customerRepository.findById(customerId);
        Customer theCustomer = null;
        if(result.isPresent()){
            theCustomer = result.get();
        } else {
            throw new RuntimeException("Customer with id - " +customerId + " not found");
        }
        return theCustomer;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        customerRepository.save(theCustomer);
    }

    @Override
    public void disableCustomer(String customerId) {
        Optional<Customer> result = customerRepository.findById(customerId);
        Customer theCustomer = null;
        if(result.isPresent()){
            theCustomer = result.get();
        } else {
            throw  new RuntimeException("Customer with id - " +customerId+ " not found");
        }
        theCustomer.getTheAccount().setStatus(false);
    }

    @Override
    public boolean checkDuplicateCustomerId(String customerId) {
        Optional<Customer> result = customerRepository.findById(customerId);
        if(result.isPresent()){
            throw new RuntimeException("Customer with id - " +customerId + " is already exist, please try again");
        }
        return true;
    }

}
