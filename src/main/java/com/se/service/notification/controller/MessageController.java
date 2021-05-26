package com.se.service.notification.controller;

import com.se.service.notification.handler.model.ErrorResponse;
import com.se.service.notification.model.request.SendNotificationRequest;
import com.se.service.notification.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Evgeniy Skiba
 */
@RestController
@RequestMapping("/message")
@Api(value = "Message management api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Notification successfully added to the send queue"),
            @ApiResponse(code = 404, message = "Incorrect template place holder for this notification",
                    response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect send message model",
                    response = ErrorResponse.class)
    })
    @ApiOperation(value = "Send notification",
            notes = "Send notification by notification group id with requirement parameters ")
    @PostMapping("/send")
    public ResponseEntity sendMessage(@Valid @RequestBody SendNotificationRequest notificationRequest) {
        messageService.sendMessageByType(notificationRequest);
        return ResponseEntity.accepted().build();
    }
}
