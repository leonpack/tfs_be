package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByAvailableStatus(Boolean value);

}
