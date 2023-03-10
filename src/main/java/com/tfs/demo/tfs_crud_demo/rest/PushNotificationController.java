package com.tfs.demo.tfs_crud_demo.rest;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tfs.demo.tfs_crud_demo.dto.MessageDTO;
import com.tfs.demo.tfs_crud_demo.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PushNotificationController {

    private FirebaseService firebaseService;

    @Autowired
    public PushNotificationController(FirebaseService theFirebaseService){
        firebaseService = theFirebaseService;
    }


    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody MessageDTO messageDTO,@RequestParam String token) throws FirebaseMessagingException {
        return firebaseService.sendNotification(messageDTO,token);
    }

}
