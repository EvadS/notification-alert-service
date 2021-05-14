package com.se.service.notification.model.request;

import com.se.service.notification.model.enums.NotificationAlertType;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationTypeItemRequest {
    private NotificationAlertType alertType;
    private boolean enabled;

    public NotificationTypeItemRequest() {
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
