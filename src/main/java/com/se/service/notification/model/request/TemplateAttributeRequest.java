package com.se.service.notification.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "TemplateAttributeRequest",
        description = "Provide information to manipulate template attribute")
public class TemplateAttributeRequest {

    @ApiModelProperty(
            value="name",
            notes = "Notification group name", required = true)
    private String name;

    public TemplateAttributeRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TemplateAttributeRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
