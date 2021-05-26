package com.se.service.notification.handler.validation;

import com.se.service.notification.handler.validation.impl.NotificationExistsValidator;

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
@Constraint(validatedBy = NotificationExistsValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface NotificationExists {

    String message() default "Notification with this id doesn't exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}