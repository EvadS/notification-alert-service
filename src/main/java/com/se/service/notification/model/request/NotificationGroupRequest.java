package com.se.service.notification.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationGroupRequest {

    @NotBlank(message = "Is required field")
    //TODO already exists validator
    private String name;

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
}
