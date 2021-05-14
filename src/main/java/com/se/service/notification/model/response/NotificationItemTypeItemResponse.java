package com.se.service.notification.model.response;

import com.se.service.notification.model.enums.NotificationAlertType;

import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationItemTypeItemResponse {
    private NotificationAlertType alertType;
    @NotNull
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
}
