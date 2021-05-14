package com.se.service.notification.model.response;

/**
 * Created by Evgeniy Skiba
 */
public class TemplateAttributeResponse {
    private long id;
    private String name;

    public TemplateAttributeResponse() {
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
}

