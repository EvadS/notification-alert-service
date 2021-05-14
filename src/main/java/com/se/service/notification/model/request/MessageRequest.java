package com.se.service.notification.model.request;

import com.se.service.notification.model.enums.NotificationAlertType;

/**
 * Created by Evgeniy Skiba
 */
public class MessageRequest {
    private NotificationAlertType alertType;
    private long notificationId;

    public MessageRequest() {
    }

    public NotificationAlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(NotificationAlertType alertType) {
        this.alertType = alertType;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }
}
