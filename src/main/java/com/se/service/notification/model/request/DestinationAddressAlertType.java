package com.se.service.notification.model.request;

import com.se.service.notification.model.enums.NotificationAlertType;

/**
 * Created by Evgeniy Skiba
 */
public class DestinationAddressAlertType {
    private NotificationAlertType alertType;
    private String destinationAddress;

    public DestinationAddressAlertType() {
    }

    public NotificationAlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(NotificationAlertType alertType) {
        this.alertType = alertType;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
