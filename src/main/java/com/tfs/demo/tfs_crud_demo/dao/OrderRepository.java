package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getAllByCustomerId(int customerId);

    List<Order> getAllByStaffId(String staffId);

    List<Order> getAllByRestaurantId(String restaurantId);

}
