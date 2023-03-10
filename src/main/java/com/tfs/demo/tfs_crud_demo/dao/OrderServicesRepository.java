package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.OrderServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface OrderServicesRepository extends JpaRepository<OrderServices, Integer> {
}
