package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.AddCommentDTO;
import com.tfs.demo.tfs_crud_demo.entity.Feedback;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.FeedbackService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FeedbackRestController {

    private FeedbackService feedbackService;

    private FoodService foodService;

    @Autowired
    public FeedbackRestController(FeedbackService theFeedbackService, FoodService theFoodService){
        feedbackService = theFeedbackService;
        foodService = theFoodService;
    }

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedbacks(){
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/feedbacks/account/{customerId}")
    public List<Feedback> getByCustomer(@PathVariable int customerId){
        return feedbackService.getAllByCustomerId(customerId);
    }

    @GetMapping("/feedbacks/food/{foodId}")
    public List<Feedback> getByFood(@PathVariable int foodId){
        Food food = foodService.getFoodById(foodId);
        return feedbackService.getAllByFood(food);
    }


    //additional get only status = true
    @GetMapping("/feedbacks/allbystatus")
    public List<Feedback> getAllPositiveFeedbacks(){
        List<Feedback> origin = feedbackService.getAllFeedbacks();
        List<Feedback> newList = new ArrayList<>();
        for(Feedback item: origin){
            if(item.getStatus().toString().equals("true")){
                newList.add(item);
            }
        }
        for(Feedback item: newList){
            System.out.println(item);
        }
        return newList;
    }

    @GetMapping("/feedbacks/allbycustomer/{customerId}")
    public List<Feedback> getAllPositiveByCustomer(@PathVariable int customerId){
        List<Feedback> origin = feedbackService.getAllByCustomerId(customerId);
        List<Feedback> newList = new ArrayList<>();
        for(Feedback item: origin){
            if(item.getStatus().toString().equals("true")){
                newList.add(item);
            }
        }
        return newList;
    }

    @GetMapping("/feedbacks/allbyfood/{foodId}")
    public List<Feedback> getAllPositiveByFood(@PathVariable int foodId){
        Food food = foodService.getFoodById(foodId);
        List<Feedback> origin = feedbackService.getAllByFood(food);
        List<Feedback> newList = new ArrayList<>();
        for(Feedback item: origin){
            if(item.getStatus().toString().equals("true")){
                newList.add(item);
            }
        }
        return newList;
    }

//    @PostMapping("/feedbacks")
//    public Feedback addNewFeedback(@RequestBody Feedback feedback){
//        if(feedback.getPoint()<0 || feedback.getPoint()>100){
//            throw new RuntimeException("Feedback point must be in range (0-100), please try again");
//        }
//        feedbackService.save(feedback);
//        return feedback;
//    }

    @PostMapping("/feedbacks")
    public Feedback addNewComment(@RequestBody AddCommentDTO addCommentDTO){
        Food food = foodService.getFoodById(addCommentDTO.getFoodId());
        Feedback feedback = new Feedback(food, addCommentDTO.getCustomerId(), addCommentDTO.getAvatarUrl(), addCommentDTO.getComment(), addCommentDTO.getRate(), true);
        feedbackService.save(feedback);
        food.addComment(feedback);
        foodService.saveFood(food);
        return feedback;
    }

    @PostMapping("/feedbacks/pack")
    public ResponseEntity<String> addListFeedBack(@RequestBody List<AddCommentDTO> list){
        for(AddCommentDTO item: list){
            Food food = foodService.getFoodById(item.getFoodId());
            Feedback feedback = new Feedback(food, item.getCustomerId(), item.getAvatarUrl(), item.getComment(), item.getRate(), true);
            feedbackService.save(feedback);
            food.addComment(feedback);
            foodService.saveFood(food);
        }
        return ResponseEntity.ok("Done");
    }

    @PutMapping("/feedbacks")
    public Feedback updateFeedback(@RequestBody Feedback feedback){
        Feedback existFeedback = feedbackService.getById(feedback.getId());
        if(feedback.getComment()==null){
            feedback.setComment(existFeedback.getComment());
        }
        if(feedback.getFood()==null){
            feedback.setFood(existFeedback.getFood());
        }
        if(feedback.getCustomerId()==null){
            feedback.setCustomerId(existFeedback.getCustomerId());
        }
        if(feedback.getRate()==null){
            feedback.setRate(existFeedback.getRate());
        }
        if(feedback.getStatus()==null){
            feedback.setStatus(feedback.getStatus());
        }
        if(feedback.getAvatarUrl()==null){
            feedback.setAvatarUrl(existFeedback.getAvatarUrl());
        }
        feedbackService.save(feedback);
        return feedback;
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    public ResponseEntity<String> disableFeedback(@PathVariable int feedbackId){
        Feedback feedback = feedbackService.getById(feedbackId);
        feedback.setFood(null);
        feedbackService.disableFeedback(feedbackId);
        return ResponseEntity.ok("Deleted!");
    }

}
