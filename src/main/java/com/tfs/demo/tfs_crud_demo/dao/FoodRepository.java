package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Category;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
@CrossOrigin
public interface FoodRepository extends JpaRepository<Food, Integer> {

    List<Food> findFoodsByPurchaseNumGreaterThan(int number);

    @Query(value = "select * from food f limit ?1", nativeQuery = true)
    List<Food> findAll(int size);

}
