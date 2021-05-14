package com.se.service.notification.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Evgeniy Skiba
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class BindTemplateException extends  RuntimeException {

    private final String expression;

    public BindTemplateException(String expression) {
        super(String.format("The '%s' has evaluated to null or missing.", expression));
        this.expression = expression;
    }
}

