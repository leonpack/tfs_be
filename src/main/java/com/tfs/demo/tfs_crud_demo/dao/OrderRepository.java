package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getAllByCustomerId(int customerId);

    List<Order> getAllByStaffId(int staffId);

    List<Order> getAllByRestaurantId(int restaurantId);

    Order getOrderByParty(Party party);

    @Query(value = "SELECT * FROM orders\n" +
            "WHERE staff_id = ?1\n" +
//            "AND orders.activity_status = 'done'\n" +
            "AND order_date >= DATE_SUB(NOW(), INTERVAL 1 MINUTE);", nativeQuery = true)
    List<Order>getAllOrdersForAutoAssign(int staffId);

}
