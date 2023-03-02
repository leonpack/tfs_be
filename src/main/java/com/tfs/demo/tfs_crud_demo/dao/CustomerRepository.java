package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer getCustomerByTheAccount(Account theAccount);

    Customer getCustomerByEmail(String email);

    Customer getCustomerByCartId(int id);

}
