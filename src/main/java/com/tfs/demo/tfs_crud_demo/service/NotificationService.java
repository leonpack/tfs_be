package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getAll();

    List<Notification> getAllByAccountId(String accountId);

    Notification save(Notification notification);

    void remove(int id);

}
