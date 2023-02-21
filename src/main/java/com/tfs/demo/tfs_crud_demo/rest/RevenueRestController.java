package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.RevenueRepository;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RevenueRestController {

    private RevenueRepository revenueRepository;

    public RevenueRestController(RevenueRepository theRevenueRepository){
        revenueRepository = theRevenueRepository;
    }

    @GetMapping("/revenues")
    public Collection<Double> getRevenueList(){
        return revenueRepository.getAllBill();
    }

    @GetMapping("/revenues/restaurant/{restaurantId}")
    public Collection<Double> getBillsByRestaurant (@PathVariable String restaurantId){
        return revenueRepository.getAllBillByRestaurant(restaurantId);
    }

}
