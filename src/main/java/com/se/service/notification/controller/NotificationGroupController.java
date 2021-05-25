package com.se.service.notification.controller;

import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
@RestController
@RequestMapping("/notification-group")
public class NotificationGroupController {

    private final NotificationService notificationService;

    public NotificationGroupController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApiOperation(value = "notification group.",
            notes = "Create notification group")
    @PostMapping
    public ResponseEntity<NotificationGroupResponse> create(@Valid @RequestBody NotificationGroupRequest request) {
        NotificationGroupResponse notificationResponse = notificationService.createNotificationGroup(request);
        return ResponseEntity.ok(notificationResponse);
    }

    @ApiOperation(value = "notification group.",
            notes = "Get information about notification group by unique identifier")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationGroupResponse> gitById(@PathVariable(value = "id") @NotNull Long id) {
        NotificationGroupResponse notificationResponse = notificationService.getGroupInfo(id);
        return ResponseEntity.ok(notificationResponse);
    }

    @ApiOperation(value = "Change notification group status.",
            notes = "Change notification group status [enabled/disabled] by unique identifier")
    @GetMapping("/status/{id}/{status}")
    public ResponseEntity<NotificationGroupResponse> changeGroupStatus(
            @PathVariable(value = "id") @NotNull Long id,
            @PathVariable(value = "status") @NotNull boolean status)
    {
        NotificationGroupResponse notificationResponse =
                notificationService.changeNotificationGroupStatus(id, status);
        return ResponseEntity.ok(notificationResponse);
    }

    @ApiOperation(value = "Notification group list.",
            notes = "Get paged available current notification list")
    @GetMapping("/list")
    public ResponseEntity<List<NotificationGroupResponse>> getList() {
        List<NotificationGroupResponse> groupList = notificationService.list();
        return ResponseEntity.ok(groupList);
    }

    @ApiOperation(value = "Update notification group",
            notes = "Update notification group by unique identifier")
    @PutMapping("/{id}")
    public ResponseEntity<NotificationGroupResponse> updateNotificationGroup(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid NotificationGroupRequest groupRequest) {
        NotificationGroupResponse groupResponse = notificationService.updateNotificationGroup(id, groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @ApiOperation(value = "Delete notification group",
            notes = "Delete notification group by unique identifier")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificationGroup(@PathVariable(value = "id") @NotNull Long id) {
        notificationService.deleteNotificationGroup(id);
        return ResponseEntity.accepted().build();
    }
}
