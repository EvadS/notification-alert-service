package com.se.service.notification.dao.repository;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Evgeniy Skiba
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByName(String requestName);

    List<Notification> findAllByNotificationGroup(NotificationGroup notificationGroup);
}