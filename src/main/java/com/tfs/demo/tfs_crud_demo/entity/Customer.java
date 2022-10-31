package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_full_name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar_url")
    private String avatarURL;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    @JsonManagedReference
    private Account theAccount;

    @Column(name = "address")
    private String address;

    public Customer(){

    }

    public Customer(String customerId, String customerName, String email, String avatarURL, Account theAccount, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.avatarURL = avatarURL;
        this.theAccount = theAccount;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
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
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
                ", theAccount=" + theAccount +
                ", address='" + address + '\'' +
                '}';
    }

}
