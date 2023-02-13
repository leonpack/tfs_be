package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getAllOrders();

    public Order getOrderById(int orderId);

    public Order saveOrder(Order order);

}
