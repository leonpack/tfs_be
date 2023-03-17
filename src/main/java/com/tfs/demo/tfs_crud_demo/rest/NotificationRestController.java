package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Notification;
import com.tfs.demo.tfs_crud_demo.service.NotificationService;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class NotificationRestController {

    private NotificationService notificationService;

    private StaffService staffService;

    private RestaurantService restaurantService;

    private OrderService orderService;

    public NotificationRestController(NotificationService theNotificationService, OrderService theOrderService, RestaurantService theRestaurantService, StaffService theStaffService){
        notificationService = theNotificationService;
        orderService = theOrderService;
        restaurantService = theRestaurantService;
        staffService = theStaffService;
    }

    @GetMapping("/notifications/byaccount/{accountId}")
    public List<Notification> getNotificationsByAccountId(@PathVariable String accountId){
        return notificationService.getAllByAccountId(accountId);
    }

    @PutMapping("/notifications/checked/{notificationId}")
    public Notification setNotificationStatus(@PathVariable int notificationId){
        Notification notification = notificationService.getById(notificationId);
        if(notification.getChecked().toString().equals("false")){
            notification.setChecked(true);
        } else if (notification.getChecked().toString().equals("true")){
            notification.setChecked(false);
        }
        return notification;
    }

    @DeleteMapping("/notification/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable int notificationId){
        notificationService.remove(notificationId);
        return ResponseEntity.ok("Done");
    }

}
