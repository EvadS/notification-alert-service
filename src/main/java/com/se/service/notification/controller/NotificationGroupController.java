package com.se.service.notification.controller;

import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.service.NotificationService;
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

    @PostMapping
    public ResponseEntity<NotificationGroupResponse> create(@Valid @RequestBody NotificationGroupRequest request) {
        NotificationGroupResponse notificationResponse = notificationService.createNotificationGroup(request);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationGroupResponse> gitById(@PathVariable(value = "id") @NotNull Long id) {
        NotificationGroupResponse notificationResponse = notificationService.getGroupInfo(id);
        return ResponseEntity.ok(notificationResponse);
    }


    @GetMapping("/status/{id}/{status}")
    public ResponseEntity<NotificationGroupResponse> changeGroupStatus(
            @PathVariable(value = "id") @NotNull Long id,
            @PathVariable(value = "status") @NotNull boolean status)
    {
        NotificationGroupResponse notificationResponse =
                notificationService.changeNotificationGroupStatus(id, status);
        return ResponseEntity.ok(notificationResponse);
    }
    @GetMapping("/list")
    public ResponseEntity<List<NotificationGroupResponse>> getList() {
        List<NotificationGroupResponse> groupList = notificationService.list();
        return ResponseEntity.ok(groupList);
    }

    @PutMapping("/{id}")
    ResponseEntity<NotificationGroupResponse> updateNotificationGroup(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid NotificationGroupRequest groupRequest) {
        NotificationGroupResponse groupResponse = notificationService.updateNotificationGroup(id, groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteNotificationGroup(@PathVariable(value = "id") @NotNull Long id) {
        notificationService.deleteNotificationGroup(id);
        return ResponseEntity.accepted().build();
    }
}
