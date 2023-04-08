package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Notification;
import com.tfs.demo.tfs_crud_demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class NotificationRestController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService theNotificationService){
        notificationService = theNotificationService;
    }

    @GetMapping("/notifications/byaccount/{accountId}")
    public List<Notification> getNotificationsByAccountId(@PathVariable String accountId){
        return notificationService.getAllByAccountId(accountId);
    }

    @PutMapping("/notifications/checked/{notificationId}")
    public Notification setNotificationStatus(@PathVariable int notificationId){
        Notification notification = notificationService.getById(notificationId);
        if(notification.getChecked().equals(false)){
            notification.setChecked(true);
            notificationService.save(notification);
        }
        else if (notification.getChecked().equals(true)){
            notification.setChecked(false);
            notificationService.save(notification);
        }
        return notification;
    }

    @DeleteMapping("/notification/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable int notificationId){
        notificationService.remove(notificationId);
        return ResponseEntity.ok("Done");
    }

}
