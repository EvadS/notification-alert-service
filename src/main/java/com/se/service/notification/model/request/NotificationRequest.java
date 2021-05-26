package com.se.service.notification.model.request;

import com.se.service.notification.handler.validation.NotificationGroupExists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "NotificationGroupResponse",
        description = "Provide model to create new notification")

public class NotificationRequest {

    @NotBlank(message = "name is required field")
    @ApiModelProperty(
            value="name",
            notes = "Notification name", required = true)
    private String name;

    @ApiModelProperty(
            value="htmlContent",
            notes = "Notification html template", required = true)
    @NotBlank(message = "html content id required field")
    private String htmlContent;


    @ApiModelProperty(
            value="parentGroupId",
            notes = "Notification group id", required = true)
    @NotificationGroupExists
    private long parentGroupId;

    @ApiModelProperty(
            value="enabled",
            notes = "Notification is not disabled flag. Disabled by default", required = true)
    private boolean enabled= false;

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
