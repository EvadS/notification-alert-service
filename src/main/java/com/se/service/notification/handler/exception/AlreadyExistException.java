package com.se.service.notification.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with %s: %s.", resourceName, fieldName, fieldValue));

    }
}
