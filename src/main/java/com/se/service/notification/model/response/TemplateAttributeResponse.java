package com.se.service.notification.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Evgeniy Skiba
 */
@ApiModel(value = "TemplateAttributeResponse",
        description = "Provide template attribute model")
public class TemplateAttributeResponse {
    @ApiModelProperty(
            value="id",
            notes = "Template attribute unique identifier", required = true)
    private long id;

    @ApiModelProperty(
            value="name",
            notes = "Template attribute name", required = true)
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

    @Override
    public String toString() {
        return "TemplateAttributeResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

