package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.RevenueRepository;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
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
