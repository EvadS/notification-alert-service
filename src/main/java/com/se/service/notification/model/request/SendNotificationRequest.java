package com.se.service.notification.model.request;

import com.se.service.notification.handler.validation.NotificationExists;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evgeniy Skiba
 */
public class SendNotificationRequest {

    @NotificationExists
    private long notificationId;

    @NotEmpty
    List<DestinationAddressAlertType> destinationAddressList = new ArrayList<>();

    Map<String, String > placeholdersMap = new HashMap<>();

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
