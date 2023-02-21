package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Category;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
@CrossOrigin
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query(value = "select category_id, event_id, region_id from food f where f.food_id=?1", nativeQuery = true)
    List<Object[]> getFoodDetail(int foodId);

}
