package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.RevenueRepository;
import com.tfs.demo.tfs_crud_demo.dto.*;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.entity.Restaurant;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class StatisticRestController {

    private RevenueRepository revenueRepository;
    private RestaurantService restaurantService;
    private CustomerService customerService;
    private StaffService staffService;
    private OrderService orderService;

    public StatisticRestController(RevenueRepository theRevenueRepository, OrderService theOrderService,
                                   RestaurantService theRestaurantService, CustomerService theCustomerService,
                                   StaffService theStaffService){
        revenueRepository = theRevenueRepository;
        orderService = theOrderService;
        restaurantService = theRestaurantService;
        customerService = theCustomerService;
        staffService = theStaffService;
    }

//    @PostMapping("/revenues/bydate")
//    public List<Order> getRevenueByDate(@RequestBody RevenueByDate revenueByDate) throws ParseException {
//        List<Order> listOrder = revenueRepository.getOrdersByOrderDate(revenueByDate.getRevenueDate());
////        Map<String, Object> returnValue = new HashMap<>();
////        Double revenue = (double) 0;
////        Integer quantity = 0;
////        for(Order item : listOrder){
////            revenue += item.getTotalPrice();
////            quantity += item.getTotalQuantity();
////            System.out.println(item);
////        }
////        returnValue.put("date", revenueByDate.getRevenueDate());
////        returnValue.put("totalquantity", quantity);
////        returnValue.put("totalrevenue", revenue);
//        return listOrder;
//    }

//    @PostMapping("/revenues/bydate/restaurant")
//    public Map<String, Object> getRevenueByDateAndRestaurant(@RequestBody RevenueByDateByRestaurant revenue){
//        List<Order> listOrder = revenueRepository.getOrdersByOrderDateAndRestaurantId(revenue.getRevenueDate().atStartOfDay(), revenue.getRestaurantId());
//        Restaurant restaurant = restaurantService.getRestaurantById(revenue.getRestaurantId());
//        Map<String, Object> returnValue = new HashMap<>();
//        Double revenues = (double) 0;
//        Integer quantity = 0;
//        for(Order item : listOrder){
//            revenues += item.getTotalPrice();
//            quantity += item.getTotalQuantity();
//        }
//        returnValue.put("restaurantid", revenue.getRestaurantId());
//        returnValue.put("restaurantname", restaurant.getRestaurantName());
//
//        returnValue.put("date", revenue.getRevenueDate());
//        returnValue.put("totalquantity", quantity);
//        returnValue.put("totalrevenue", revenues);
//
//        return returnValue;
//    }

//    @PostMapping("/revenues/between")
//    public Map<String, Object> getRevenueBetween(@RequestBody RevenueBetweenDTO revenueBetweenDTO){
//        List<Order> listOrder = revenueRepository.getOrdersByOrderDateBetween(revenueBetweenDTO.getFromDate().atStartOfDay(), revenueBetweenDTO.getToDate().atStartOfDay());
//        Map<String, Object> returnValue = new HashMap<>();
//        Double revenue = (double) 0;
//        Integer quantity = 0;
//        for(Order item: listOrder){
//            revenue += item.getTotalPrice();
//            quantity += item.getTotalQuantity();
//        }
//
//        returnValue.put("fromdate", revenueBetweenDTO.getFromDate());
//        returnValue.put("todate", revenueBetweenDTO.getToDate());
//        returnValue.put("totalquantity",quantity);
//        returnValue.put("totalrevenue", revenue);
//
//        return returnValue;
//    }

//    @PostMapping("/revenues/between/restaurant")
//    public Map<String, Object> getRevenueBetweenByRestaurant(@RequestBody RevenueBetweenByRestaurantDTO revenue){
//        List<Order> listOrder = revenueRepository.getOrdersByOrderDateBetweenAndRestaurantId(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay(), revenue.getRestaurantId());
//        Restaurant restaurant = restaurantService.getRestaurantById(revenue.getRestaurantId());
//        Map<String, Object> returnValue = new HashMap<>();
//        Double revenues = (double) 0;
//        Integer quantity = 0;
//        for(Order item: listOrder){
//            quantity += item.getTotalQuantity();
//            revenues += item.getTotalPrice();
//        }
//
//        returnValue.put("restaurantid", revenue.getRestaurantId());
//        returnValue.put("restaurantname", restaurant.getRestaurantName());
//        returnValue.put("fromdate", revenue.getFromDate());
//        returnValue.put("todate", revenue.getToDate());
//        returnValue.put("totalquantity", quantity);
//        returnValue.put("totalrevenue", revenue);
//
//        return returnValue;
//    }

    @GetMapping("/statistic")
    public Map<String, Object> getAllStatistic(){
        List<Order> getAllOrders = orderService.getAllOrders();
        List<Customer> getAllCustomers = customerService.getAllCustomers();
        List<Staff> getAllStaffs = staffService.getAllStaffs();
        Double totalRevenues = (double) 0;
        for(Order item: getAllOrders){
            totalRevenues += item.getTotalPrice();
        }
        int orderNum = getAllOrders.size();
        int customerNum = getAllCustomers.size();
        int staffNum = getAllStaffs.size();

        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("totalorders", orderNum);
        returnValue.put("totalcustomers", customerNum);
        returnValue.put("totalstaffs", staffNum);
        returnValue.put("totalrevenues", totalRevenues);

        return returnValue;
    }

    @GetMapping("/statistic/byrestaurant/{restaurantId}")
    public Map<String, Object> getStatisticByRestaurant(@PathVariable int restaurantId){
        List<Order> getOrdersByRestaurant = orderService.getAllOrderByRestaurantId(restaurantId);
        List<Staff> getStaffsByRestaurant = staffService.getAllByRestaurant(restaurantService.getRestaurantById(restaurantId));
        int ordersNum = getOrdersByRestaurant.size();
        int staffsNum = getStaffsByRestaurant.size();
        Double totalRevenue = (double) 0;
        for(Order item: getOrdersByRestaurant){
            totalRevenue += item.getTotalPrice();
        }

        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("totalorders", ordersNum);
        returnValue.put("totalstaffs", staffsNum);
        returnValue.put("totalrevenues", totalRevenue);

        return returnValue;
    }

    @PostMapping("/statistic/detail")
    public Collection<OrderDateResponse> getDetailFromRestaurant(@RequestBody RevenueBetweenByRestaurantDTO revenue){
        return revenueRepository.getOrdersFilterByDate(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay(), revenue.getRestaurantId());
    }

    @PostMapping("statistic/detail/owner")
    public Collection<OrderDateResponse> getDetailForOwner(@RequestBody RevenueBetweenDTO revenue){
        return revenueRepository.getOrdersFilterByDateForOwner(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay());
    }
}
