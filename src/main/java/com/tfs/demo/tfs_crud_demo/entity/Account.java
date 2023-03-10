package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "status")
    private boolean status;

    @OneToOne(mappedBy = "theAccount",cascade = CascadeType.ALL)
    @JsonBackReference(value = "account-customer")
    private Customer theCustomer;


    @OneToOne(mappedBy = "theAccountForStaff", cascade = CascadeType.ALL)
//    @JsonBackReference(value = "account-staff")
    private Staff theStaff;

    public Account(){

    }

    public Account(String accountId, String phoneNumber, String password, Integer roleId, boolean status) {
        this.accountId = accountId;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roleId = roleId;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Customer getTheCustomer() {
        return theCustomer;
    }

    public void setTheCustomer(Customer theCustomer) {
        this.theCustomer = theCustomer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
