package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.RevenueRepository;
import com.tfs.demo.tfs_crud_demo.dto.RevenueBetweenByRestaurantDTO;
import com.tfs.demo.tfs_crud_demo.dto.RevenueBetweenDTO;
import com.tfs.demo.tfs_crud_demo.dto.RevenueByDate;
import com.tfs.demo.tfs_crud_demo.dto.RevenueByDateByRestaurant;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.entity.Restaurant;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RevenueRestController {

    private RevenueRepository revenueRepository;
    private RestaurantService restaurantService;

    private OrderService orderService;

    public RevenueRestController(RevenueRepository theRevenueRepository, OrderService theOrderService, RestaurantService theRestaurantService){
        revenueRepository = theRevenueRepository;
        orderService = theOrderService;
        restaurantService = theRestaurantService;
    }

    @PostMapping("/revenues/bydate")
    public Map<String, Object> getRevenueByDate(@RequestBody RevenueByDate revenueByDate){
        List<Order> listOrder = revenueRepository.getOrdersByOrderDate(revenueByDate.getRevenueDate());
        Map<String, Object> returnValue = new HashMap<>();
        Double revenue = (double) 0;
        Integer quantity = 0;
        for(Order item : listOrder){
            revenue += item.getTotalPrice();
            quantity += item.getTotalQuantity();
        }
        returnValue.put("date", revenueByDate.getRevenueDate());
        returnValue.put("totalquantity", quantity);
        returnValue.put("totalrevenue", revenue);

        return returnValue;
    }

    @PostMapping("/revenues/bydate/restaurant")
    public Map<String, Object> getRevenueByDateAndRestaurant(@RequestBody RevenueByDateByRestaurant revenue){
        List<Order> listOrder = revenueRepository.getOrdersByOrderDateAndRestaurantId(revenue.getRevenueDate(), revenue.getRestaurantId());
        Restaurant restaurant = restaurantService.getRestaurantById(revenue.getRestaurantId());
        Map<String, Object> returnValue = new HashMap<>();
        Double revenues = (double) 0;
        Integer quantity = 0;
        for(Order item : listOrder){
            revenues += item.getTotalPrice();
            quantity += item.getTotalQuantity();
        }
        returnValue.put("restaurantid", revenue.getRestaurantId());
        returnValue.put("restaurantname", restaurant.getRestaurantName());

        returnValue.put("date", revenue.getRevenueDate());
        returnValue.put("totalquantity", quantity);
        returnValue.put("totalrevenue", revenue);

        return returnValue;
    }

    @PostMapping("/revenues/between")
    public Map<String, Object> getRevenueBetween(@RequestBody RevenueBetweenDTO revenueBetweenDTO){
        List<Order> listOrder = revenueRepository.getOrdersByOrderDateBetween(revenueBetweenDTO.getFromDate(), revenueBetweenDTO.getToDate());
        Map<String, Object> returnValue = new HashMap<>();
        Double revenue = (double) 0;
        Integer quantity = 0;
        for(Order item: listOrder){
            revenue += item.getTotalPrice();
            quantity += item.getTotalQuantity();
        }

        returnValue.put("fromdate", revenueBetweenDTO.getFromDate());
        returnValue.put("todate", revenueBetweenDTO.getToDate());
        returnValue.put("totalquantity",quantity);
        returnValue.put("totalrevenue", revenue);

        return returnValue;
    }

    @PostMapping("/revenues/between/restaurant")
    public Map<String, Object> getRevenueBetweenByRestaurant(@RequestBody RevenueBetweenByRestaurantDTO revenue){
        List<Order> listOrder = revenueRepository.getOrdersByOrderDateBetweenAndRestaurantId(revenue.getFromDate(), revenue.getToDate(), revenue.getRestaurantId());
        Restaurant restaurant = restaurantService.getRestaurantById(revenue.getRestaurantId());
        Map<String, Object> returnValue = new HashMap<>();
        Double revenues = (double) 0;
        Integer quantity = 0;
        for(Order item: listOrder){
            quantity += item.getTotalQuantity();
            revenues += item.getTotalPrice();
        }

        returnValue.put("restaurantid", revenue.getRestaurantId());
        returnValue.put("restaurantname", restaurant.getRestaurantName());
        returnValue.put("fromdate", revenue.getFromDate());
        returnValue.put("todate", revenue.getToDate());
        returnValue.put("totalquantity", quantity);
        returnValue.put("totalrevenue", revenue);

        return returnValue;
    }


}
