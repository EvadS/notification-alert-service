package com.se.service.notification.model.response;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationResponse {

    private long id;

    private String name;

    private boolean enabled;

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
