package com.se.service.notification.service;

import com.se.service.notification.model.request.MessageRequest;
import com.se.service.notification.model.request.SendNotificationRequest;

/**
 * Created by Evgeniy Skiba
 */

public interface MessageService {

    void sendMessageByType(SendNotificationRequest notificationRequest);

}
