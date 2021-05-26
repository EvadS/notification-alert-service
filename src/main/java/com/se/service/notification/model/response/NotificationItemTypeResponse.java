package com.se.service.notification.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "NotificationResponse",
        description = "Provide information about notification")
public class NotificationItemTypeResponse {

    @ApiModelProperty(
            value = "id",
            notes = "Notification unique identifier", required = true)
    private long notificationItemId;

    @ApiModelProperty(
            value = "notificationItemTypeItemResponseList",
            notes = "The list with available chanel type to set enable/disabled each alert type", required = true)
    private List<NotificationItemTypeItemResponse> notificationItemTypeItemResponseList = new ArrayList<>();

    public NotificationItemTypeResponse() {
    }

    public long getNotificationItemId() {
        return notificationItemId;
    }

    public void setNotificationItemId(long notificationItemId) {
        this.notificationItemId = notificationItemId;
    }

    public List<NotificationItemTypeItemResponse> getNotificationItemTypeItemResponseList() {
        return notificationItemTypeItemResponseList;
    }

    public void setNotificationItemTypeItemResponseList(List<NotificationItemTypeItemResponse> notificationItemTypeItemResponseList) {
        this.notificationItemTypeItemResponseList = notificationItemTypeItemResponseList;
    }

    @Override
    public String toString() {
        return "NotificationItemTypeResponse{" +
                "notificationItemId=" + notificationItemId +
                ", notificationItemTypeItemResponseList=" + notificationItemTypeItemResponseList +
                '}';
    }
}
