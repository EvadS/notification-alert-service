package com.se.service.notification.model.request;

import com.se.service.notification.model.enums.NotificationAlertType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */

@ApiModel(value = "DestinationAddressAlertType",
        description = "Provide recipient  with notifications type ")
public class DestinationAddressAlertType {
    @NotNull
    @ApiModelProperty(
            value="alertType",
            notes = "Notification type to send message  ", required = true)
    private NotificationAlertType alertType;

    @NotBlank(message = "Address is required field")
    @ApiModelProperty(
            value="destinationAddress",
            notes = "Address of recipient by this notification type ", required = true)
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

    @Override
    public String toString() {
        return "DestinationAddressAlertType{" +
                "alertType=" + alertType +
                ", destinationAddress='" + destinationAddress + '\'' +
                '}';
    }
}
