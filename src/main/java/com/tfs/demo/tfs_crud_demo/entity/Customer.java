package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "customer_full_name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar_url")
    private String avatarURL;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    @JsonManagedReference(value = "account-customer")
    private Account theAccount;

//    @OneToMany(mappedBy = "theCustomerOrder",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JsonManagedReference(value = "customer-order")
//    private List<Order> orderList;

//    @OneToOne(mappedBy = "theCustomerCart",cascade = CascadeType.ALL)
//    @JsonBackReference(value = "cart-customer")
//    private Cart theCart;

    @Column(name = "address")
    private String address;

    public Customer(){

    }

    public Customer(String customerName, String email, String avatarURL, Account theAccount, String address) {
        this.customerName = customerName;
        this.email = email;
        this.avatarURL = avatarURL;
        this.theAccount = theAccount;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public Account getTheAccount() {
        return theAccount;
    }

    public void setTheAccount(Account theAccount) {
        this.theAccount = theAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", theAccount=" + theAccount +
                ", address='" + address + '\'' +
                '}';
    }

//    public void addOrder(Order theOrder){
//        if(orderList == null){
//            orderList = new ArrayList<>();
//        }
//        orderList.add(theOrder);
//    }
}
