package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.dto.OrderDateForOwnerResponse;
import com.tfs.demo.tfs_crud_demo.dto.OrderDateResponse;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
@CrossOrigin
public interface RevenueRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select total_price from orders", nativeQuery = true)
    Collection<Double> getAllBill();

    @Query(value = "select total_price from orders o where o.restaurant_id = ?1 ", nativeQuery = true)
    Collection<Double> getAllBillByRestaurant(String restaurantId);

    List<Order> getOrdersByOrderDate(LocalDateTime orderDate);

    List<Order> getOrdersByOrderDateAndRestaurantId(LocalDateTime orderDate, int restaurantId);

    List<Order> getOrdersByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<Order> getOrdersByOrderDateBetweenAndRestaurantId(LocalDateTime fromDate, LocalDateTime toDate, int restaurantId);

    @Query(value = "SELECT DATE_FORMAT(order_date,'%Y-%m-%d') as orderDay, \n" +
            "       SUM(total_price) as totalPrice, \n" +
            "       SUM(total_quantity) as totalQuantity \n" +
            "FROM orders \n" +
            "WHERE order_date BETWEEN ?1 AND ?2 AND restaurant_id = ?3\n" +
            "GROUP BY orderDay \n" +
            "ORDER BY orderDay ASC;\n", nativeQuery = true)
    Collection<OrderDateResponse> getOrdersFilterByDate(LocalDateTime fromDate, LocalDateTime toDate, int restaurantId);


    @Query(value = "SELECT DATE_FORMAT(order_date,'%Y-%m-%d') as orderDay, \n" +
            "       SUM(total_price) as totalPrice, \n" +
            "       SUM(total_quantity) as totalQuantity \n" +
            "FROM orders \n" +
            "WHERE order_date BETWEEN ?1 AND ?2\n" +
            "GROUP BY orderDay \n" +
            "ORDER BY orderDay ASC;\n", nativeQuery = true)
    Collection<OrderDateForOwnerResponse> getOrdersFilterByDateForOwner(LocalDateTime fromDate, LocalDateTime toDate);

}
