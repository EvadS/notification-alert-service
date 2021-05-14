package com.se.service.notification.service.impl;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationType;
import com.se.service.notification.dao.repository.NotificationTypeRepository;
import com.se.service.notification.handler.exception.ResourceNotFoundException;
import com.se.service.notification.model.enums.NotificationAlertType;
import com.se.service.notification.model.request.NotificationTypeItemRequest;
import com.se.service.notification.model.response.NotificationItemTypeResponse;
import com.se.service.notification.service.NotificationItemTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Evgeniy Skiba on 13.05.21
 * implemented logic to work with notification types such as (email, sms, etc)
 */
@Service
public class NotificationItemTypeServiceImpl implements NotificationItemTypeService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationItemTypeServiceImpl.class);

    private final NotificationTypeRepository notificationItemTypeRepository;

    public NotificationItemTypeServiceImpl(NotificationTypeRepository notificationItemTypeRepository) {
        this.notificationItemTypeRepository = notificationItemTypeRepository;
    }

    @Override
    public void createDefaultNotificationTypesByItem(Notification notificationItem) {

        final String availableAlertType = String.join(",",
                Arrays.asList(NotificationAlertType.values()).stream().map(Object::toString).collect(Collectors.toList()));

        logger.info("Create notification to base alert types:[{}]",availableAlertType);

        Arrays.asList(NotificationAlertType.values()).stream().forEach(alertType -> {
            NotificationType notificationItemType = new NotificationType(alertType, notificationItem);
            notificationItemTypeRepository.save(notificationItemType);
        });
    }

    @Override
    public void changeStatusNotificationTypeById(Long NotificationItemTypeId, boolean isEnabled) {
        NotificationType notificationItemType = notificationItemTypeRepository.findById(NotificationItemTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification item type", "id", NotificationItemTypeId));

        notificationItemType.setEnabled(isEnabled);
        notificationItemTypeRepository.save(notificationItemType);
    }

    @Override
    public List<NotificationType> getCurrentNotificationByNotificationItem(Notification notificationItem) {
        return notificationItemTypeRepository.findAllByNotification(notificationItem);
    }

    @Override
    public List<NotificationType> getEnabledNotificationByNotificationItem(Notification notificationItem) {
        return notificationItemTypeRepository.findAllByNotificationAndEnabled(notificationItem, true);
    }

    @Override
    public NotificationItemTypeResponse changeNotificationAlertStatus(Notification notification, List<NotificationTypeItemRequest> notificationTypeItemRequests) {

        notificationItemTypeRepository.findAllByNotification(notification).stream().forEach(it-> {
           //change by each
            // changeNotificationAlertStatus();
        });

        return null;
    }
}
