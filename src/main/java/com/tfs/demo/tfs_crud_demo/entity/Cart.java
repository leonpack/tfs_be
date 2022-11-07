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
    @JsonManagedReference
    private Customer theCustomerCart;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "cartdetail", joinColumns = @JoinColumn(name = "cart_id"),inverseJoinColumns = @JoinColumn(name = "food_id"))
    @JsonManagedReference
    private List<Food> foodInCartList;

    public Cart(){

    }

    public Cart(Customer theCustomerCart) {
        this.theCustomerCart = theCustomerCart;
    }

    public Cart(List<Food> foodInCartList) {
        this.foodInCartList = foodInCartList;
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

    public List<Food> getFoodInCartList() {
        return foodInCartList;
    }

    public void setFoodInCartList(List<Food> foodInCartList) {
        this.foodInCartList = foodInCartList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", theCustomerCart=" + theCustomerCart +
                ", foodInCartList=" + foodInCartList +
                '}';
    }

    public void addFood(Food theFood){
        if(foodInCartList==null){
            foodInCartList = new ArrayList<>();
        }
        foodInCartList.add(theFood);
    }

    public void removeFood(Food theFood){
        foodInCartList.remove(theFood);
    }

}
