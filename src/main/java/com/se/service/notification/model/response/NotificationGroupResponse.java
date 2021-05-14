package com.se.service.notification.model.response;

/**
 * Created by Evgeniy Skiba
 */


public class NotificationGroupResponse {
    private long id;
    private String name;
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
}
