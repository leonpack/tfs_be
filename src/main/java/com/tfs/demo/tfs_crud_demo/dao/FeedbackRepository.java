package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Feedback;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> getFeedbacksByCustomerIdOrderByCreatedAtDesc(int accountId);

    List<Feedback> getFeedbacksByFoodOrderByCreatedAtDesc(Food food);
}
