package com.se.service.notification.controller;

import com.se.service.notification.handler.model.ErrorResponse;
import com.se.service.notification.model.request.NotificationRequest;
import com.se.service.notification.model.request.NotificationTypeRequest;
import com.se.service.notification.model.response.NotificationBaseResponse;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.model.response.NotificationItemTypeResponse;
import com.se.service.notification.model.response.NotificationResponse;
import com.se.service.notification.service.NotificationService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved notification",
                    response = NotificationResponse.class),
            @ApiResponse(code = 404, message = "Notification was not found by unique identifier", response = ErrorResponse.class)
    })
    @GetMapping("/{id}")
    @ApiOperation(value = "Notification",
            notes = "Notification details by id", tags = {})
    public ResponseEntity<NotificationResponse> getNotificationItem(
            @ApiParam(value = "notification unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id) {
        NotificationResponse notificationResponse = notificationService.getNotificationItem(id);
        return ResponseEntity.ok(notificationResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved created notification",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "Incorrect template placeholders", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Incorrect notification model to create. Already exists", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to create notification group", response = ErrorResponse.class),
    })
    @PostMapping
    @ApiOperation(value = "Create notification", notes = "Create  new notification")
    public ResponseEntity<NotificationBaseResponse> createNotificationItem(
            @Valid @RequestBody NotificationRequest notificationRequest) {
        NotificationBaseResponse notificationResponse = notificationService.createNotificationItem(notificationRequest);
        return ResponseEntity.ok(notificationResponse);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated notification",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 409, message = "Incorrect notification name to create notification group", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to update notification ", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Notification doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @PutMapping("/{id}")
    @ApiOperation(value = "Update Notification",
            notes = "Update Notification by request model and uniques notification id")
    public ResponseEntity<NotificationBaseResponse> updateNotificationItem(
            @ApiParam(value = "notification  unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid NotificationRequest notificationRequest) {
        NotificationBaseResponse notificationResponse = notificationService.updateNotificationItem(id, notificationRequest);
        return ResponseEntity.ok(notificationResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated notification status",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "Notification doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @GetMapping("/status/{id}/{status}")
    @ApiOperation(value = "Update Notification",
            notes = "Change notification status (enabled/disabled) according status path param")
    public ResponseEntity<NotificationGroupResponse> changeGroupStatus(
            @ApiParam(value = "notification unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id,
            @PathVariable(value = "status") @NotNull boolean status) {
        notificationService.changeNotificationItemStatus(id, status);
        return ResponseEntity.accepted().build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "notification success deleted",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "Notification doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Notification",
            notes = "Delete Notification by unique identifoer")
    public ResponseEntity deleteNotificationItem(
            @ApiParam(value = "notification group unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id) {
        notificationService.deleteNotificationItem(id);
        return ResponseEntity.accepted().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Available notifications ",
                    response = NotificationResponse.class),
     })
    @GetMapping(value = "/list", produces = "application/json")
    @ApiOperation(value = "Current Notification",
            notes = "Paged available notifications.")
    public ResponseEntity<Page<NotificationResponse>> pagedTemplateList(
            @ApiParam(value = "Start index", required = true, example = "0")
            @Min(value = 0, message = "Paged list started from zero index")
            @RequestParam(name = "page", defaultValue = "0") int page,
            @ApiParam(value = "Page size", required = true, example = "0")
            @Min(value = 1, message = "Page size must not be less than one")
            @RequestParam(name = "size", defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(DEFAULT_SEARCH_PARAM).descending());

        Page<NotificationResponse> pagedTemplateResponse = notificationService.pagedNotificationResponse(pageable);
        return ResponseEntity.ok(pagedTemplateResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Available alert type ",
                    response = NotificationItemTypeResponse.class),
    })
    @ApiOperation(value = "Alert types ",
            notes = "Change alert type status by notifications")
    @PostMapping("/alert-types")
    public ResponseEntity<NotificationItemTypeResponse> setAlertTypeStatus(
            @Valid @RequestBody NotificationTypeRequest notificationRequest) {

        NotificationItemTypeResponse notificationItemTypeResponse =
                notificationService.setNotificationAlertTypes(notificationRequest);
        return ResponseEntity.ok(notificationItemTypeResponse);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Notification chanel status after chanching ",
                    response = NotificationItemTypeResponse.class),
            @ApiResponse(code = 404, message = "Notification doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @ApiOperation(value = "Set alert type",
            notes = "Enabled/disabled alert status by notification unique identifier")
    @GetMapping("/alert-types/{notification-item-id}")
    public ResponseEntity<NotificationItemTypeResponse> getAlertType(
            @ApiParam(value = "notification unique identifier", required = true, example = "1")
            @PathVariable(value = "notification-item-id") @NotNull Long notificationId) {

        NotificationItemTypeResponse notificationItemTypeResponse = notificationService.getNotificationAlertTypes(notificationId);
        return ResponseEntity.ok(notificationItemTypeResponse);
    }
}
