package com.se.service.notification.dao.repository;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    List<NotificationType> findAllByNotification(Notification notification);

    List<NotificationType> findAllByNotificationAndEnabled(Notification notificationItem, boolean isEnabled);
}
