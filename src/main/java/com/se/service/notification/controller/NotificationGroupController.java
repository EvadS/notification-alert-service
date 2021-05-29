package com.se.service.notification.controller;

import com.se.service.notification.controller.base.NotificationGroupControllerBase;
import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
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
public class NotificationGroupController implements NotificationGroupControllerBase {

    private final NotificationService notificationService;

    public NotificationGroupController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @PostMapping
    public ResponseEntity<NotificationGroupResponse> create(@Valid @RequestBody NotificationGroupRequest request) {
        NotificationGroupResponse notificationResponse = notificationService.createNotificationGroup(request);
        return ResponseEntity.ok(notificationResponse);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NotificationGroupResponse> gitById
            (@PathVariable(value = "id") @NotNull Long id) {
        NotificationGroupResponse notificationResponse = notificationService.getGroupInfo(id);
        return ResponseEntity.ok(notificationResponse);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<NotificationGroupResponse> updateNotificationGroup(
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid NotificationGroupRequest groupRequest) {
        NotificationGroupResponse groupResponse = notificationService.updateNotificationGroup(id, groupRequest);
        return ResponseEntity.ok(groupResponse);
    }

    @Override
    @GetMapping("/status/{id}/{status}")
    public ResponseEntity<NotificationGroupResponse> changeGroupStatus(
            @PathVariable(value = "id") @NotNull Long id,
            @PathVariable(value = "status") @NotNull boolean status) {
        NotificationGroupResponse notificationResponse =
                notificationService.changeNotificationGroupStatus(id, status);
        return ResponseEntity.ok(notificationResponse);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<NotificationGroupResponse>> getList() {
        List<NotificationGroupResponse> groupList = notificationService.list();
        return ResponseEntity.ok(groupList);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNotificationGroup(
            @PathVariable(value = "id") @NotNull Long id) {
        notificationService.deleteNotificationGroup(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
