package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    public List<Order> getAllOrders();

    public List<Order> getAllOrderByCustomerId(int customerId);

    public List<Order> getAllOrderByStaffId(String staffId);

    public List<Order> getAllOrderByRestaurantId(int restaurantId);

    public Order getOrderById(int orderId);

    public Order saveOrder(Order order);

    public void deleteOrder(int orderId);

    public boolean CheckDuplicateOrderId(int orderId);

}
