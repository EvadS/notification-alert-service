package com.se.service.notification.service;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationType;
import com.se.service.notification.model.request.NotificationTypeItemRequest;
import com.se.service.notification.model.response.NotificationItemTypeResponse;

import java.util.List;

/**
 * Created by Evgeniy Skiba on 13.05.21
 */
public interface NotificationItemTypeService {
    void createDefaultNotificationTypesByItem(Notification notificationItem);

    void changeStatusNotificationTypeById(Long NotificationItemTypeId, boolean isEnabled);

    List<NotificationType> getCurrentNotificationByNotificationItem(Notification notificationItem);

    List<NotificationType> getEnabledNotificationByNotificationItem(Notification notificationItem);

    NotificationItemTypeResponse changeNotificationAlertStatus(Notification notification, List<NotificationTypeItemRequest> notificationTypeItemRequests);
}
