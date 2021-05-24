package com.se.service.notification.service.impl;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.handler.exception.IncorrectTemplateException;
import com.se.service.notification.handler.exception.ResourceNotFoundException;
import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.request.DestinationAddressAlertType;
import com.se.service.notification.model.request.SendNotificationRequest;
import com.se.service.notification.service.MessageService;
import com.se.service.notification.service.TemplateBuilderService;
import com.se.service.notification.service.notification.NotificationAlertsFactory;
import com.se.service.notification.service.notification.NotificationAlertsStrategy;
import org.springframework.stereotype.Service;

/**
 * Created by Evgeniy Skiba
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final TemplateBuilderService templateService;
    private final NotificationAlertsFactory alertsFactory;
    private final NotificationRepository notificationRepository;

    public MessageServiceImpl(TemplateBuilderService templateService, NotificationAlertsFactory alertsFactory,
                              NotificationRepository notificationRepository) {
        this.templateService = templateService;
        this.alertsFactory = alertsFactory;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendMessageByType(SendNotificationRequest notificationRequest) {

        Notification notification = notificationRepository.findById(notificationRequest.getNotificationId())
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationRequest.getNotificationId()));

        if (!templateService.isTemplateValid(notification.getHtmlPart())) {
            throw new IncorrectTemplateException("Html content has incorrect expresion.Compare " +
                    "Notification placeholders and current templates variables ");
        }
        String messageBody = templateService.bindTemplate(notification.getHtmlPart(), notificationRequest.getPlaceholdersMap());

        for (DestinationAddressAlertType destinationAddress : notificationRequest.getDestinationAddressList()) {

            NotificationAlertsStrategy alertsStrategy =
                    alertsFactory.findStrategy(destinationAddress.getAlertType());

            NotificationModel notificationModel = new NotificationModel();

            notificationModel.setHtmlBody(messageBody);
            notificationModel.setRecipient(destinationAddress.getDestinationAddress());
            notificationModel.setSubject(notificationRequest.getSubject());

            alertsStrategy.sendNotification(notificationModel);
        }
    }
}
