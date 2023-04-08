package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findNotificationsByAccountIdOrderByCreatedAtDesc(String accountId);

}
