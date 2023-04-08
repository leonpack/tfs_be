package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.NotificationRepository;
import com.tfs.demo.tfs_crud_demo.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImplementation implements NotificationService{

    private final NotificationRepository notificationRepository;

    public NotificationServiceImplementation(NotificationRepository theNotificationRepository){
        notificationRepository = theNotificationRepository;
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getAllByAccountId(String accountId) {
        return notificationRepository.findNotificationsByAccountIdOrderByCreatedAtDesc(accountId);
    }

    @Override
    public Notification getById(int notificationId) {
        Optional<Notification> result = notificationRepository.findById(notificationId);
        Notification notification = null;
        if(result.isPresent()){
            notification = result.get();
        } else {
            throw new RuntimeException("Notification not found");
        }
        return notification;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void remove(int id) {
        Optional<Notification> result = notificationRepository.findById(id);
        Notification notification = null;
        if(result.isPresent()){
            notification = result.get();
            notificationRepository.delete(notification);
        } else {
            throw new RuntimeException("Notification not found");
        }
    }

}
