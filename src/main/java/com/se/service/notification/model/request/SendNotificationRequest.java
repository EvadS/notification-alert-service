package com.se.service.notification.model.request;

import com.se.service.notification.handler.validation.NotificationExists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "SendNotificationRequest",
        description = "Provide model to send notification")
public class SendNotificationRequest {

    @NotEmpty(message = "should be minimum one recipient to send notification")
    @ApiModelProperty(
            value="destinationAddressList",
            notes = "List of notification recipient with address according to communication type", required = true)
    List<DestinationAddressAlertType> destinationAddressList = new ArrayList<>();


    @ApiModelProperty(
            value="placeholdersMap",
            notes = "Key values to set value to each placeholder.")
    Map<String, String> placeholdersMap = new HashMap<>();


    @ApiModelProperty(
            value="notificationId",
            notes = "id of notification with required template", required = true)
    @NotificationExists
    private long notificationId;


    @ApiModelProperty(
            value="subject",
            notes = "Notification subject", required = true)
    private String subject;

    public SendNotificationRequest() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<DestinationAddressAlertType> getDestinationAddressList() {
        return destinationAddressList;
    }

    public void setDestinationAddressList(List<DestinationAddressAlertType> destinationAddressList) {
        this.destinationAddressList = destinationAddressList;
    }

    public Map<String, String> getPlaceholdersMap() {
        return placeholdersMap;
    }

    public void setPlaceholdersMap(Map<String, String> placeholdersMap) {
        this.placeholdersMap = placeholdersMap;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }
}
