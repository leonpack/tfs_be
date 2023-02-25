package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Feedback;
import com.tfs.demo.tfs_crud_demo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FeedbackRestController {

    private FeedbackService feedbackService;

    @Autowired
    public FeedbackRestController(FeedbackService theFeedbackService){
        feedbackService = theFeedbackService;
    }

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedbacks(){
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/feedbacks/food/{foodId}")
    public List<Feedback> getByFood(@PathVariable int foodId){
        return feedbackService.getAllByFoodId(foodId);
    }

    @GetMapping("/feedbacks/customer/{customerId}")
    public List<Feedback> getByCustomer(@PathVariable int customerId){
        return feedbackService.getAllByCustomerId(customerId);
    }

    @PostMapping("/feedbacks")
    public Feedback addNewFeedback(@RequestBody Feedback feedback){
        if(feedback.getPoint()<0 || feedback.getPoint()>100){
            throw new RuntimeException("Feedback point must be in range (0-100), please try again");
        }
        feedbackService.save(feedback);
        return feedback;
    }

    @PutMapping("/feedbacks")
    public Feedback updateFeedback(@RequestBody Feedback feedback){
        Feedback existFeedback = feedbackService.getById(feedback.getId());
        if(feedback.getComment()==null){
            feedback.setComment(existFeedback.getComment());
        }
        if(feedback.getFoodId()==null){
            feedback.setFoodId(existFeedback.getFoodId());
        }
        if(feedback.getCustomerId()==null){
            feedback.setCustomerId(existFeedback.getCustomerId());
        }
        if(feedback.getPoint()==null){
            feedback.setPoint(existFeedback.getPoint());
        }
        if(feedback.getStatus()==null){
            feedback.setStatus(feedback.getStatus());
        }
        feedbackService.save(feedback);

        return feedback;
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    public String disableFeedback(@PathVariable int feedbackId){
        feedbackService.disableFeedback(feedbackId);
        return "Disabled!";
    }

}
