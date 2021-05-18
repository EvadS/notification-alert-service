package com.se.service.notification.controller;

import com.se.service.notification.model.request.NotificationRequest;
import com.se.service.notification.model.request.NotificationTypeRequest;
import com.se.service.notification.model.response.NotificationBaseResponse;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.model.response.NotificationResponse;
import com.se.service.notification.model.response.NotificationItemTypeResponse;
import com.se.service.notification.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// TODO: add api to work with notification types

/**
 * Created by Evgeniy Skiba
 */
@RestController
@RequestMapping("/notification")
@Api(value = "Notification items api")
public class NotificationController {

    public static final String DEFAULT_SEARCH_PARAM = "createdAt";

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Notification",
            notes = "Notification details by id", tags = {})
    public ResponseEntity<NotificationResponse> getNotificationItem(@PathVariable(value = "id") @NotNull Long id){
        NotificationResponse notificationResponse = notificationService.getNotificationItem(id);
        return  ResponseEntity.ok(notificationResponse);
    }

    @PostMapping
    @ApiOperation(value = "Create notification", notes = "Create  new notification")
    public ResponseEntity<NotificationBaseResponse> createNotificationItem(
            @Valid @RequestBody NotificationRequest notificationRequest){
        NotificationBaseResponse notificationResponse = notificationService.createNotificationItem(notificationRequest);
        return ResponseEntity.ok(notificationResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Notification",
            notes = "Update Notification by request model and uniques notification id")
    public ResponseEntity<NotificationBaseResponse> updateNotificationItem(@PathVariable(value = "id") @NotNull Long id,
                                                                @RequestBody @Valid NotificationRequest notificationRequest){
        NotificationBaseResponse notificationResponse = notificationService.updateNotificationItem(id, notificationRequest);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/status/{id}/{status}")
    @ApiOperation(value = "Update Notification",
            notes = "Change notification status (enabled/disabled) according status path param")
    public ResponseEntity<NotificationGroupResponse> changeGroupStatus(
            @PathVariable(value = "id") @NotNull Long id,
            @PathVariable(value = "status") @NotNull boolean status) {
        notificationService.changeNotificationItemStatus(id, status);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificationItem(@PathVariable(value = "id") @NotNull Long id){
        notificationService.deleteNotificationItem(id);
        return  ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<Page<NotificationResponse>> pagedTemplateList(
            @Min(value = 0, message = "Paged list started from zero index")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Min(value = 1, message = "Page size must not be less than one")
            @RequestParam(name = "size", defaultValue = "1") int size){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(DEFAULT_SEARCH_PARAM).descending());

        Page<NotificationResponse> pagedTemplateResponse = notificationService.pagedNotificationResponse(pageable);
        return ResponseEntity.ok(pagedTemplateResponse);
    }

    @PostMapping("/alert-types")
    public  ResponseEntity<NotificationItemTypeResponse> setAlertTypeStatus(@Valid @RequestBody NotificationTypeRequest notificationRequest){
        NotificationItemTypeResponse notificationItemTypeResponse =
                notificationService.setNotificationAlertTypes(notificationRequest);

        return  ResponseEntity.ok(notificationItemTypeResponse);
    }

    @GetMapping("/alert-types/{notification-item-id}")
    public  ResponseEntity<NotificationItemTypeResponse> getAlertType(@PathVariable(value = "notification-item-id") @NotNull Long notificationId){
        NotificationItemTypeResponse notificationItemTypeResponse = notificationService.getNotificationAlertTypes(notificationId);
        return  ResponseEntity.ok(notificationItemTypeResponse);
    }
}
