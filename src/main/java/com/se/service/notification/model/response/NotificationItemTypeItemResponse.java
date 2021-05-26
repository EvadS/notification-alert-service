package com.se.service.notification.model.response;

import com.se.service.notification.model.enums.NotificationAlertType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "NotificationItemTypeItemResponse",
        description = "Provide information about enable/disable status for each notification type")
public class NotificationItemTypeItemResponse {

    @ApiModelProperty(
            value = "alertType",
            notes = "Notification Alert Type to send notification by one of chanel (sms, email)", required = true)
    private NotificationAlertType alertType;


    @ApiModelProperty(
            value = "enabled",
            notes = "Disable/ enable using this chanel type", required = true)
    private boolean enabled;

    public NotificationItemTypeItemResponse() {
    }

    public NotificationAlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(NotificationAlertType alertType) {
        this.alertType = alertType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "NotificationItemTypeItemResponse{" +
                "alertType=" + alertType +
                ", enabled=" + enabled +
                '}';
    }
}
