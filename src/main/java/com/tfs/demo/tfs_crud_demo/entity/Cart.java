package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer theCustomerCart;


    public Cart(){

    }

    public Cart(Customer theCustomerCart) {
        this.theCustomerCart = theCustomerCart;
    }


    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Customer getTheCustomerCart() {
        return theCustomerCart;
    }

    public void setTheCustomerCart(Customer theCustomerCart) {
        this.theCustomerCart = theCustomerCart;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", theCustomerCart=" + theCustomerCart +
                '}';
    }
}
