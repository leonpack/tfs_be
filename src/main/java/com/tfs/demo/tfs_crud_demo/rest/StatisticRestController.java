package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.RevenueRepository;
import com.tfs.demo.tfs_crud_demo.dto.*;
import com.tfs.demo.tfs_crud_demo.entity.Customer;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class StatisticRestController {

    private final RevenueRepository revenueRepository;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;
    private final StaffService staffService;
    private final OrderService orderService;

    public StatisticRestController(RevenueRepository theRevenueRepository, OrderService theOrderService,
                                   RestaurantService theRestaurantService, CustomerService theCustomerService,
                                   StaffService theStaffService){
        revenueRepository = theRevenueRepository;
        orderService = theOrderService;
        restaurantService = theRestaurantService;
        customerService = theCustomerService;
        staffService = theStaffService;
    }

    @GetMapping("/statistic")
    public Map<String, Object> getAllStatistic(){
        List<Order> getAllOrders = orderService.getAllOrders();
        List<Customer> getAllCustomers = customerService.getAllCustomers();
        List<Staff> getAllStaffs = staffService.getAllStaffs();
        Double totalRevenues = (double) 0;
        Double prepaid = (double) 0;
        for(Order item: getAllOrders){
            totalRevenues += item.getTotalPrice();
            if(item.getPaymentMethod().equals("ZaloPay") && item.getStatus().equals("deny")){
                prepaid += (item.getTotalPrice()*0.1);
            }
        }
        int orderNum = getAllOrders.size();
        int customerNum = getAllCustomers.size();
        int staffNum = getAllStaffs.size();

        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("totalorders", orderNum);
        returnValue.put("totalcustomers", customerNum);
        returnValue.put("totalstaffs", staffNum);
        returnValue.put("totalrevenues", totalRevenues);
        returnValue.put("totalrefunds", prepaid);
        returnValue.put("totalfinal", totalRevenues + prepaid);

        return returnValue;
    }

    @GetMapping("/statistic/byrestaurant/{restaurantId}")
    public Map<String, Object> getStatisticByRestaurant(@PathVariable int restaurantId){
        List<Order> getOrdersByRestaurant = orderService.getAllOrderByRestaurantId(restaurantId);
        List<Staff> getStaffsByRestaurant = staffService.getAllByRestaurant(restaurantService.getRestaurantById(restaurantId));
        int ordersNum = getOrdersByRestaurant.size();
        int staffsNum = getStaffsByRestaurant.size();
        double prepaid = (double) 0;
        Double totalRevenue = (double) 0;
        for(Order item: getOrdersByRestaurant){
            totalRevenue += item.getTotalPrice();
            if(item.getStatus().equals("deny") && item.getPaymentMethod().equals("ZaloPay")){
                prepaid += (item.getTotalPrice()*0.1);
            }
        }

        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("totalorders", ordersNum);
        returnValue.put("totalstaffs", staffsNum);
        returnValue.put("totalrevenues", totalRevenue);
        returnValue.put("totalrefunds", prepaid);
        returnValue.put("totalfinal", totalRevenue + prepaid);

        return returnValue;
    }

    @PostMapping("/statistic/detail")
    public Collection<OrderDateResponse> getDetailFromRestaurant(@RequestBody RevenueBetweenByRestaurantDTO revenue){
        if(revenue.getFromDate().isAfter(revenue.getToDate())){
            throw new RuntimeException("fromDate and toDate input is incorrect");
        }
        return revenueRepository.getOrdersFilterByDate(revenue.getFromDate().minusDays(1).atStartOfDay(), revenue.getToDate().atStartOfDay(), revenue.getRestaurantId());
    }

    @PostMapping("statistic/detail/owner")
    public Collection<OrderDateResponse> getDetailForOwner(@RequestBody RevenueBetweenDTO revenue){
        if(revenue.getFromDate().isAfter(revenue.getToDate())){
            throw new RuntimeException("fromDate and toDate input is incorrect");
        }
        return revenueRepository.getOrdersFilterByDateForOwner(revenue.getFromDate().minusDays(1).atStartOfDay(), revenue.getToDate().atStartOfDay());
    }

    @PostMapping("/statistic/detail/user")
    public Map<String, Object> getUserBetween(@RequestBody RevenueBetweenByRestaurantDTO revenue){
        Map<String, Object> result = new HashMap<>();
        List<Order> orderList = revenueRepository.getOrdersByOrderDateBetweenAndRestaurantId(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay(), revenue.getRestaurantId());
        int totalUser = 0;
        for(Order item: orderList){
            if(item.getStatus().equals("done")){
                totalUser += 1;
            }
        }
        result.put("totaluser", totalUser);
        return result;
    }

    @PostMapping("/statistic/detail/owner/user")
    public Map<String, Object> getUserForOwner(@RequestBody RevenueBetweenDTO revenue){
        Map<String, Object> result = new HashMap<>();
        List<Order> orderList = revenueRepository.getOrdersByOrderDateBetween(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay());
        int totalUser = 0;
        for(Order item: orderList){
            if(item.getStatus().equals("done")){
                totalUser += 1;
            }
        }
        result.put("totaluser", totalUser);
        return result;
    }

    @PostMapping("/statistic/refund/detail/owner")
    public Collection<RefundChartResponseDTO> getRefundChartForOwner(@RequestBody RevenueBetweenDTO revenue){
        if(revenue.getFromDate().isAfter(revenue.getToDate())){
            throw new RuntimeException("fromDate and toDate value is not valid, please try again");
        }
        return revenueRepository.getRefundChartForOwner(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay());
    }

    @PostMapping("/statistic/refund/detail")
    public Collection<RefundChartResponseDTO> getRefundChartForRestaurant(@RequestBody RevenueBetweenByRestaurantDTO revenue){
        if(revenue.getFromDate().isAfter(revenue.getToDate())){
            throw new RuntimeException("fromDate and toDate value is not valid, please try again");
        }
        return revenueRepository.getRefundChartForRestaurant(revenue.getFromDate().atStartOfDay(), revenue.getToDate().atStartOfDay(), revenue.getRestaurantId());
    }

    @GetMapping("/statistic/staff/{staffId}")
    public Collection<StaffChartResponseDTO> getStaffStatistic(@PathVariable int staffId){
        return revenueRepository.getStaffStatistic(staffId);
    }



}
