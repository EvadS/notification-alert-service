package com.se.service.notification.controller.base;

import com.se.service.notification.handler.model.ErrorResponse;
import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.response.NotificationGroupResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Evgeniy Skiba on 26.05.21
 */
@Api(value = "Notification Group management api")
public interface NotificationGroupControllerBase {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved created notification group",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 409, message = "Incorrect notification group name to create notification group", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to create notification group", response = ErrorResponse.class),
    })
    @ApiOperation(value = "Notification group.",
            notes = "Create notification group")
    ResponseEntity<NotificationGroupResponse> create(@Valid @RequestBody NotificationGroupRequest request);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved  notification group",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "Notification group was not found by unique identifier", response = ErrorResponse.class)
    })
    @ApiOperation(value = "Notification group information.",
            notes = "Get information about notification group by unique identifier")
    ResponseEntity<NotificationGroupResponse> gitById(
            @ApiParam(value = "notification group unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated notification group",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 400, message = "Incorrect notification group name to create notification group", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Incorrect model to update notification group", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Notification group doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @ApiOperation(value = "Update notification group",
            notes = "Update notification group by unique identifier")
    @PutMapping("/{id}")
    ResponseEntity<NotificationGroupResponse> updateNotificationGroup(
            @ApiParam(value = "notification group unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id,
            @RequestBody @Valid NotificationGroupRequest groupRequest);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated notification group status",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "Notification group doesn't exists by unique identifier", response = ErrorResponse.class)
    })

    @ApiOperation(value = "Change notification group status.",
            notes = "Change notification group status [enabled/disabled] with sub-group notification by unique identifier")
    ResponseEntity<NotificationGroupResponse> changeGroupStatus(
            @ApiParam(value = "notification group unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id,
            @ApiParam(value = "notification group new status", required = true)
            @PathVariable(value = "status") @NotNull boolean status);

    @ApiOperation(value = "Notification group list.",
            notes = "Get paged available current notification list")
    ResponseEntity<List<NotificationGroupResponse>> getList();

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "notification group success deleted",
                    response = NotificationGroupResponse.class),
            @ApiResponse(code = 404, message = "Notification group doesn't exists by unique identifier", response = ErrorResponse.class)
    })
    @ApiOperation(value = "Delete notification group",
            notes = "Delete notification group by unique identifier")
    ResponseEntity deleteNotificationGroup(
            @ApiParam(value = "notification group unique identifier", required = true, example = "1")
            @PathVariable(value = "id") @NotNull Long id);
}
