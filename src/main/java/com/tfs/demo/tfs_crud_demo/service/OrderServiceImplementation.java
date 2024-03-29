package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.OrderRepository;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.entity.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImplementation(OrderRepository theOrderRepository){
        orderRepository = theOrderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrderByCustomerId(int customerId) {
        return orderRepository.getAllByCustomerId(customerId);
    }

    @Override
    public List<Order> getAllOrderByStaffId(int staffId) {
        return orderRepository.getAllByStaffId(staffId);
    }

    @Override
    public List<Order> getAllOrderByRestaurantId(int restaurantId) {
        return orderRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Order> getAllOrdersForAutoAssign(int staffId) {
        return orderRepository.getAllOrdersForAutoAssign(staffId);
    }

    @Override
    public Order getByParty(Party party) {
        return orderRepository.getOrderByParty(party);
    }

    @Override
    public Order getOrderById(int orderId) {
        Optional<Order> result = orderRepository.findById(orderId);
        Order order = null;
        if(result.isPresent()){
            order = result.get();
        } else {
            throw new RuntimeException("Order with id - " +orderId + " not found!");
        }
        return order;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        Optional<Order> result = orderRepository.findById(orderId);
        Order order = null;
        if(result.isPresent()){
            order = result.get();
        } else {
            throw new RuntimeException("Order with id - " +orderId + " not found!");
        }
        orderRepository.delete(order);
    }

    @Override
    public boolean CheckDuplicateOrderId(int orderId) {
        Optional<Order> result = orderRepository.findById(orderId);
        if(result.isPresent()){
            return true;
        }
        return false;
    }

}
