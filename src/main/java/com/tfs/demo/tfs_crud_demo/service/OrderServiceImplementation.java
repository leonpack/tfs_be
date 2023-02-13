package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.OrderRepository;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService{

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImplementation(OrderRepository theOrderRepository){
        orderRepository = theOrderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
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

}
