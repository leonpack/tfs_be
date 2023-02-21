package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService theOrderService){
        orderService = theOrderService;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable int orderId){
        Order order = orderService.getOrderById(orderId);
        return order;
    }

    @GetMapping("/orders/customer/{cusId}")
    public List<Order> getAllOrderByCustomer(@PathVariable int cusId){
        return orderService.getAllOrderByCustomerId(cusId);
    }

    @GetMapping("/orders/staff/{staffId}")
    public List<Order> getAllOrderByStaff(@PathVariable String staffId){
        return orderService.getAllOrderByStaffId(staffId);
    }

    @GetMapping("/orders/restaurant/{resId}")
    public List<Order> getAllOrderByRestaurant(@PathVariable String resId){
        return orderService.getAllOrderByRestaurantId(resId);
    }

    @PostMapping("/orders")
    public Order addNewOrder(@RequestBody Order order){
        orderService.saveOrder(order);
        return order;
    }

    @PutMapping("/orders")
    public Order updateOrder(@RequestBody Order order){
        Order theTempOrder = orderService.getOrderById(order.getId());

        //try catch null input for update
        if(order.getCustomerId()==null){
            order.setCustomerId(theTempOrder.getCustomerId());
        }

        if(order.getRestaurantId()==null){
            order.setRestaurantId(theTempOrder.getRestaurantId());
        }

        if(order.getStaffId()==null){
            order.setStaffId(theTempOrder.getStaffId());
        }

        if(order.getItemList()==null){
            order.setItemList(theTempOrder.getItemList());
        }

        if(order.getOrderDate()==null){
            order.setOrderDate(theTempOrder.getOrderDate());
        }

        if(order.getDeliveryAddress()==null){
            order.setDeliveryAddress(theTempOrder.getDeliveryAddress());
        }

        if(order.getPaymentMethod()==null){
            order.setPaymentMethod(theTempOrder.getPaymentMethod());
        }

        if(order.getReceiveTime()==null){
            order.setReceiveTime(theTempOrder.getReceiveTime());
        }

        if(order.getDeliveryDate()==null){
            order.setDeliveryDate(theTempOrder.getDeliveryDate());
        }

        if(order.getTotalPrice()==null){
            order.setTotalPrice(theTempOrder.getTotalPrice());
        }

        if(order.getTotalQuantity()==null){
            order.setTotalQuantity(theTempOrder.getTotalQuantity());
        }

        orderService.saveOrder(order);
        return order;
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable int orderId){
        orderService.deleteOrder(orderId);
        return "Deleted order " + orderId;
    }


}
