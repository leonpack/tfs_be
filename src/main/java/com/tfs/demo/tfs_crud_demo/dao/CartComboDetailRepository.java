package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.CartComboDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface CartComboDetailRepository extends JpaRepository<CartComboDetail, Integer> {
}
