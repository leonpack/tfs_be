package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.FeedbackRepository;
import com.tfs.demo.tfs_crud_demo.entity.Feedback;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
@CrossOrigin
public class FeedbackServiceImplementation implements FeedbackService{

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImplementation(FeedbackRepository theFeedbackRepository){
        feedbackRepository = theFeedbackRepository;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> getAllByCustomerId(int customerId) {
        return feedbackRepository.getFeedbacksByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    @Override
    public List<Feedback> getAllByFood(Food food) {
        return feedbackRepository.getFeedbacksByFoodOrderByCreatedAtDesc(food);
    }


    @Override
    public Feedback getById(int feedbackId) {
        Optional<Feedback> result = feedbackRepository.findById(feedbackId);
        Feedback feedback = null;
        if(result.isPresent()){
            feedback = result.get();
        } else {
            throw new RuntimeException("Feed back with id - " +feedbackId + " not found!");
        }
        return feedback;
    }

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void disableFeedback(int feedbackId) {
        Optional<Feedback> result = feedbackRepository.findById(feedbackId);
        Feedback feedback = null;
        if(result.isPresent()){
            feedback = result.get();
            feedbackRepository.delete(feedback);
        } else {
            throw new RuntimeException("Feed back with id - " +feedbackId + " not found");
        }
    }

}
