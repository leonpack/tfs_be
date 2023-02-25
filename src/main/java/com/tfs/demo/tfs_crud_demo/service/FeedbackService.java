package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    public List<Feedback> getAllFeedbacks();

    public List<Feedback> getAllByFoodId(int foodId);

    public List<Feedback> getAllByCustomerId(int customerId);

    public Feedback getById(int feedbackId);

    public Feedback save(Feedback feedback);

    public void disableFeedback(int feedbackId);

}
