package com.se.service.notification.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationItemTypeResponse {

    private  long  notificationITemId;
    private List<NotificationItemTypeItemResponse> notificationItemTypeItemResponseList = new ArrayList<>();

    public NotificationItemTypeResponse() {
    }

    public long getNotificationITemId() {
        return notificationITemId;
    }

    public void setNotificationITemId(long notificationITemId) {
        this.notificationITemId = notificationITemId;
    }

    public List<NotificationItemTypeItemResponse> getNotificationItemTypeItemResponseList() {
        return notificationItemTypeItemResponseList;
    }

    public void setNotificationItemTypeItemResponseList(List<NotificationItemTypeItemResponse> notificationItemTypeItemResponseList) {
        this.notificationItemTypeItemResponseList = notificationItemTypeItemResponseList;
    }
}
