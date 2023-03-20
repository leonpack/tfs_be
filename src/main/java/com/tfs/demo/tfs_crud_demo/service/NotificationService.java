package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Notification;
import reactor.core.publisher.Flux;

import java.util.List;

public interface NotificationService {

    List<Notification> getAll();

    List<Notification> getAllByAccountId(String accountId);

    Notification getById(int notificationId);

    Notification save(Notification notification);

    void remove(int id);

}
