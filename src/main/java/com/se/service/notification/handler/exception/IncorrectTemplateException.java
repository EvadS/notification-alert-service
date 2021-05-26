package com.se.service.notification.handler.exception;

/**
 * Created by Evgeniy Skiba
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectTemplateException extends RuntimeException {
    public IncorrectTemplateException(String message) {
        super(message);
    }
}
