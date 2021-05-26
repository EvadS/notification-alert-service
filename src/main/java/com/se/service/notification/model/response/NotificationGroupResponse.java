package com.se.service.notification.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Evgeniy Skiba
 */

@ApiModel(value = "NotificationGroupResponse",
        description = "Provide information about available notification group")
public class NotificationGroupResponse {

    @ApiModelProperty(
            value="id",
            notes = "Notification group unique identifier", required = true)
    private long id;

    @ApiModelProperty(
            value="name",
            notes = "Notification group name", required = true)
    private String name;

    @ApiModelProperty(
            value="status",
            notes = "Notification group status. Provide information about notification group can used or disabled", required = true)
    private boolean status;

    public NotificationGroupResponse() {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NotificationGroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
