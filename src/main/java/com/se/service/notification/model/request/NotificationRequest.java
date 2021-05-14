package com.se.service.notification.model.request;

import com.se.service.notification.handler.validation.NotificationGroupExists;

import javax.validation.constraints.NotBlank;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationRequest {

    private String name;

    @NotBlank(message = "html content id required field")
    private String htmlContent;

    @NotificationGroupExists
    private long parentGroupId;

    private boolean enabled;

    public NotificationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public long getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(long parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "name='" + name + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", parentGroupId=" + parentGroupId +
                ", enabled=" + enabled +
                '}';
    }
}
