package com.se.service.notification.handler.validation.impl;

import com.se.service.notification.dao.repository.NotificationGroupRepository;
import com.se.service.notification.handler.validation.NotificationGroupExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationGroupExistsValidator
        implements ConstraintValidator<NotificationGroupExists, Long> {

    private final NotificationGroupRepository notificationGroupRepository;

    public NotificationGroupExistsValidator(NotificationGroupRepository notificationGroupRepository) {
        this.notificationGroupRepository = notificationGroupRepository;
    }

    @Override
    public boolean isValid(Long parentId, ConstraintValidatorContext constraintValidatorContext) {
        return notificationGroupRepository.existsById(parentId);
    }
}

