package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
@CrossOrigin
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findNotificationsByAccountIdOrderByCreatedAtDesc(String accountId);

}
