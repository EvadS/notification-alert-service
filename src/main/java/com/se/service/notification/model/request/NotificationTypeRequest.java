package com.se.service.notification.model.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationTypeRequest {

    private long notificationItemId;
    private List <NotificationTypeItemRequest> notificationTypeItemRequests = new ArrayList<>();

    public NotificationTypeRequest() {
    }

    public long getNotificationItemId() {
        return notificationItemId;
    }

    public void setNotificationItemId(long notificationItemId) {
        this.notificationItemId = notificationItemId;
    }

    public List<NotificationTypeItemRequest> getNotificationTypeItemRequests() {
        return notificationTypeItemRequests;
    }

    public void setNotificationTypeItemRequests(List<NotificationTypeItemRequest> notificationTypeItemRequests) {
        this.notificationTypeItemRequests = notificationTypeItemRequests;
    }
}
