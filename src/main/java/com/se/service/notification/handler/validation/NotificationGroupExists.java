package com.se.service.notification.handler.validation;

import com.se.service.notification.handler.validation.impl.NotificationGroupExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Evgeniy Skiba
 */
@Documented
@Constraint(validatedBy = NotificationGroupExistsValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface NotificationGroupExists {

    String message() default "Notification group with this id doesn't exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}