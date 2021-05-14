package com.se.service.notification.handler.exception;

import com.se.service.notification.handler.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotificationApiException extends RuntimeException {

    private final  List<String> details = new ArrayList<>();
    public NotificationApiException(String message, List<String> details) {
        super(message);
        this.details.addAll(details);
    }

    public NotificationApiException(ErrorResponse apiError) {
        super(apiError.getMessage());
    }
}
