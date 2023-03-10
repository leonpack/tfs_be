package com.tfs.demo.tfs_crud_demo.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.tfs.demo.tfs_crud_demo.dto.MessageDTO;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotification(MessageDTO messageDTO, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(messageDTO.getTitle())
                .setBody(messageDTO.getMessage())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
//                .putAllData(note.getData())
                .build();

        return firebaseMessaging.send(message);
    }

}
