package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> getFeedbacksByFoodId(int foodId);

    List<Feedback> getFeedbacksByCustomerId(int customerId);

}
