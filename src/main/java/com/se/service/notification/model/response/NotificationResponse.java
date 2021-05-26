package com.se.service.notification.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "NotificationResponse",
        description = "Provide information about notification")
public class NotificationResponse {
    @ApiModelProperty(
            value="id",
            notes = "Notification unique identifier", required = true)
    private long id;

    @ApiModelProperty(
            value="name",
            notes = "Notification name", required = true)
    private String name;

    @ApiModelProperty(
            value="enabled",
            notes = "Notification status. Provide information about notification group can used or disabled", required = true)
    private boolean enabled;

    @ApiModelProperty(
            value="placeHoldersList",
            notes = "List with all placeholders used in this notification template", required = true)
    private Set<String> placeHoldersList = new HashSet<>();

    public NotificationResponse() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<String> getPlaceHoldersList() {
        return placeHoldersList;
    }

    public void setPlaceHoldersList(Set<String> placeHoldersList) {
        this.placeHoldersList = placeHoldersList;
    }

    public Set<String> addToPlaceHolders(String placeHolderName) {
        this.placeHoldersList.add(placeHolderName);
        return placeHoldersList;
    }
}
