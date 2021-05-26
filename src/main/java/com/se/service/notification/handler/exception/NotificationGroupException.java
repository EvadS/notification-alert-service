package com.se.service.notification.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Evgeniy Skiba on 13.05.21
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class NotificationGroupException extends RuntimeException {

    private final String resourceName;


    public NotificationGroupException(String resourceName) {
        super(String.format("Can't change %s. There is exists child resource", resourceName));
        this.resourceName = resourceName;

    }

    public String getResourceName() {
        return resourceName;
    }

}
