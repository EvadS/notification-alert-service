package com.se.service.notification.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "NotificationGroupRequest",
        description = "Provide notification group model")
public class NotificationGroupRequest {

    @ApiModelProperty(value = "name",
            notes = "Provide the name to notification group",
            required = true)
    @NotBlank(message = "Notification group name is required field")
    private String name;

    @ApiModelProperty(value = "Notification Group status",
            notes = "Provide new group status [enabled/disabled]",
            required = true, dataType = "boolean")
    private boolean enabled;

    public NotificationGroupRequest() {
    }

    public NotificationGroupRequest(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "NotificationGroupRequest{" +
                "name='" + name + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
