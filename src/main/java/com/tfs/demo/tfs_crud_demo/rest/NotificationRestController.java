package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.NotificationRepository;
import com.tfs.demo.tfs_crud_demo.entity.Notification;
import com.tfs.demo.tfs_crud_demo.service.NotificationService;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class NotificationRestController {

    private NotificationService notificationService;
    private StaffService staffService;
    private RestaurantService restaurantService;
    private OrderService orderService;
    private final NotificationRepository notificationRepository;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    public NotificationRestController(NotificationService theNotificationService, OrderService theOrderService, RestaurantService theRestaurantService, StaffService theStaffService,
                                      NotificationRepository notificationRepository){
        notificationService = theNotificationService;
        orderService = theOrderService;
        restaurantService = theRestaurantService;
        staffService = theStaffService;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/notifications/byaccount/{accountId}")
    public List<Notification> getNotificationsByAccountId(@PathVariable String accountId){
        return notificationService.getAllByAccountId(accountId);
    }

    //NOT WORKING
//    @GetMapping(value = "/notifications/streaming/{accountId}")
//    public SseEmitter streamNotification(@PathVariable String accountId) throws IOException {
//        //khởi tạo SseEmitter mới với timeout là 15 phút
//        SseEmitter emitter = new SseEmitter(300*1000L);
//
//            //tạo 1 scheduler để sau mỗi 5 giây sẽ refresh notification mới từ database
//            scheduler.scheduleAtFixedRate(() -> {
//                List<Notification> currentData = new ArrayList<>();
//                List<Notification> newData = notificationService.getAllByAccountId(accountId);
//                //kiểm tra data có bị trùng không, nếu không trùng thì đưa vào list riêng
//                if(!newData.equals(currentData)){
//                    List<Notification> diff = new ArrayList<>(newData);
//                    //xoá hết những data bị trùng thuộc data cũ
//                    diff.removeAll(currentData);
//                    if(!diff.isEmpty()){
//                        try{
//                            for(Notification item: diff){
//                                emitter.send(item);
//                            }
//                            //sau khi gửi data mới lên thì biến đống data đã gửi thành data hiện tại và tiếp tục vòng lặp
//                            currentData = newData;
//                        } catch (IOException e){
//                            //nếu bị IO thì cho complete emitter và tắt scheduler
//                            emitter.complete();
//                            scheduler.shutdown();
//                        }
//                    }
//                }
//            }, 0, 5, TimeUnit.SECONDS);
//
//            emitter.onTimeout(emitter::complete);
//        return emitter;
//    }

    @GetMapping("/notifications/testing")
    public SseEmitter gettingNewStuff(){
        SseEmitter emitter = new SseEmitter(240 * 1000L);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                emitter.send(randomThing());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 5, TimeUnit.SECONDS);

        emitter.onTimeout(emitter::complete);

        return emitter;
    }

    @Scheduled(fixedDelay = 5000)
    public String randomThing(){
        return "Hôm nay là " + System.currentTimeMillis();
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
