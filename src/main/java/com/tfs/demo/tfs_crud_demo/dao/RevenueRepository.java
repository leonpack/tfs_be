package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.dto.OrderDateResponse;
import com.tfs.demo.tfs_crud_demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
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

    //TESTING NEW Query
//    @Query(value = "SELECT DATE_FORMAT(order_date,'%Y-%m-%d') as orderDay, \n" +
//            "       SUM(total_price) as totalPrice, \n" +
//            "       SUM(total_quantity) as totalQuantity \n" +
//            "FROM orders \n" +
//            "WHERE order_date BETWEEN ?1 AND ?2 AND restaurant_id = ?3\n" +
//            "GROUP BY orderDay \n" +
//            "ORDER BY orderDay ASC;\n", nativeQuery = true)
//    Collection<OrderDateResponse> getOrdersFilterByDate(LocalDateTime fromDate, LocalDateTime toDate, int restaurantId);

    @Query(value = "SELECT date_table.date AS orderDay, \n" +
            "    COALESCE(SUM(orders.total_price), 0) AS totalPrice, \n" +
            "    COALESCE(SUM(orders.total_quantity), 0) AS totalQuantity \n" +
            "FROM (\n" +
            "    SELECT DATE_FORMAT(DATE_ADD(?1, INTERVAL seq DAY), '%Y-%m-%d') AS date \n" +
            "    FROM (\n" +
            "        SELECT @rownum\\:=@rownum+1 AS seq \n" +
            "        FROM information_schema.columns \n" +
            "        JOIN (SELECT @rownum\\:=0) r \n" +
            "        WHERE @rownum<DATEDIFF(?2, ?1) + 1\n" +
            "    ) AS seq_table\n" +
            ") AS date_table \n" +
            "LEFT JOIN orders ON DATE_FORMAT(orders.order_date, '%Y-%m-%d') = date_table.date \n" +
            "\tAND orders.restaurant_id = ?3 \n" +
            "    AND orders.activity_status = 'done'\n" +
            "WHERE date_table.date BETWEEN ?1 AND ?2 \n" +
            "GROUP BY orderDay \n" +
            "ORDER BY orderDay ASC", nativeQuery = true)
    Collection<OrderDateResponse> getOrdersFilterByDate(LocalDateTime fromDate, LocalDateTime toDate, int restaurantId);


//    @Query(value = "SELECT DATE_FORMAT(order_date,'%Y-%m-%d') as orderDay, \n" +
//            "       SUM(total_price) as totalPrice, \n" +
//            "       SUM(total_quantity) as totalQuantity \n" +
//            "FROM orders \n" +
//            "WHERE order_date BETWEEN ?1 AND ?2\n" +
//            "GROUP BY orderDay \n" +
//            "ORDER BY orderDay ASC;\n", nativeQuery = true)
//    Collection<OrderDateForOwnerResponse> getOrdersFilterByDateForOwner(LocalDateTime fromDate, LocalDateTime toDate);

    @Query(value = "SELECT date_table.date AS orderDay, \n" +
            "    COALESCE(SUM(orders.total_price), 0) AS totalPrice, \n" +
            "    COALESCE(SUM(orders.total_quantity), 0) AS totalQuantity \n" +
            "FROM (\n" +
            "    SELECT DATE_FORMAT(DATE_ADD(?1, INTERVAL seq DAY), '%Y-%m-%d') AS date \n" +
            "    FROM (\n" +
            "        SELECT @rownum\\:=@rownum+1 AS seq \n" +
            "        FROM information_schema.columns \n" +
            "        JOIN (SELECT @rownum\\:=0) r \n" +
            "        WHERE @rownum<DATEDIFF(?2, ?1) + 1\n" +
            "    ) AS seq_table\n" +
            ") AS date_table \n" +
            "LEFT JOIN orders ON DATE_FORMAT(orders.order_date, '%Y-%m-%d') = date_table.date \n" +
            "    AND orders.activity_status = 'done'\n" +
            "WHERE date_table.date BETWEEN ?1 AND ?2 \n" +
            "GROUP BY orderDay \n" +
            "ORDER BY orderDay ASC", nativeQuery = true)
    Collection<OrderDateResponse> getOrdersFilterByDateForOwner(LocalDateTime fromDate, LocalDateTime toDate);
}
