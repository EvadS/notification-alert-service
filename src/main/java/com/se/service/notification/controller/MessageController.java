package com.se.service.notification.controller;

import com.se.service.notification.model.request.SendNotificationRequest;
import com.se.service.notification.service.MessageService;
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
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody SendNotificationRequest notificationRequest) {
        messageService.sendMessageByType(notificationRequest);
        return ResponseEntity.accepted().build();

    }
}
