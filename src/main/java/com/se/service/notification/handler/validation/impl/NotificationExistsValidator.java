package com.se.service.notification.handler.validation.impl;

import com.se.service.notification.dao.repository.NotificationGroupRepository;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.handler.validation.NotificationExists;
import com.se.service.notification.handler.validation.NotificationGroupExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationExistsValidator
        implements ConstraintValidator<NotificationExists, Long> {

    private final NotificationRepository notificationRepository;

    public NotificationExistsValidator(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public boolean isValid(Long notificationId, ConstraintValidatorContext constraintValidatorContext) {
        return notificationRepository.existsById(notificationId);
    }
}

