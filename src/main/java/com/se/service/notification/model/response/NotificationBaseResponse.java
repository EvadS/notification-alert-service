package com.se.service.notification.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "NotificationGroupResponse",
        description = "Provide full information about notification")

public class NotificationBaseResponse {
    private long id;

    @ApiModelProperty(
            value="name",
            notes = "Notification name", required = true)
    private String name;

    @ApiModelProperty(
            value="htmlContent",
            notes = "Notification template ", required = true)
    private String htmlContent;


    @ApiModelProperty(
            value="parentGroupId",
            notes = "Notification group id", required = true)
    private long parentGroupId;

    @ApiModelProperty(
            value="enabled",
            notes = "Notification is not disabled flag", required = true)
    private boolean enabled;


    public NotificationBaseResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
