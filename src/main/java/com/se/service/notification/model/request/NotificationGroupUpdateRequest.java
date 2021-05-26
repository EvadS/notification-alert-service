package com.se.service.notification.model.request;

import javax.validation.constraints.NotBlank;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationGroupUpdateRequest {

    @NotBlank(message = "Is required field")
    private String name;

    private boolean enabled;

    public NotificationGroupUpdateRequest() {
    }

    public NotificationGroupUpdateRequest(String name, boolean enabled) {
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
