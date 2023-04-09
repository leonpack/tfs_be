package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.AddCommentDTO;
import com.tfs.demo.tfs_crud_demo.entity.Feedback;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import com.tfs.demo.tfs_crud_demo.service.FeedbackService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FeedbackRestController {

    private final FeedbackService feedbackService;
    private final FoodService foodService;
    private final CustomerService customerService;

    @Autowired
    public FeedbackRestController(FeedbackService theFeedbackService, FoodService theFoodService, CustomerService theCustomerService){
        feedbackService = theFeedbackService;
        foodService = theFoodService;
        customerService = theCustomerService;
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
    public List<Map<String, Object>> getAllPositiveByFood(@PathVariable int foodId){
        List<Map<String, Object>> listResult = new ArrayList<>();
        Food food = foodService.getFoodById(foodId);
        List<Feedback> origin = feedbackService.getAllByFood(food);
        List<Feedback> newList = new ArrayList<>();
        for(Feedback item: origin){
            if(item.getStatus().toString().equals("true")){
                newList.add(item);
            }
        }
        for(Feedback item: newList){
            Map<String, Object> fb = new HashMap<>();
            fb.put("id",item.getId());
            fb.put("food",item.getFood());
            fb.put("customerName", customerService.getCustomerById(item.getCustomerId()).getCustomerName());
            fb.put("avatarUrl",item.getAvatarUrl());
            fb.put("comment",item.getComment());
            fb.put("rate",item.getRate());
            fb.put("createdAt",item.getCreatedAt());
            listResult.add(fb);
        }
        return listResult;
    }

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
            food.setRatingNum(food.getRatingNum()+1);
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
