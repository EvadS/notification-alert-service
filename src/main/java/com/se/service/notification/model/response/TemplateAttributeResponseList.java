package com.se.service.notification.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba on 26.05.21
 */
@ApiModel(value = "TemplateAttributeResponseList",
        description = "Provide list template attributes model")
public class TemplateAttributeResponseList {

    @ApiModelProperty(
            value = "responseList",
            notes = "List of template attributes", required = true)

    private List<TemplateAttributeResponse> responseList = new ArrayList<>();

    public TemplateAttributeResponseList() {
    }

    public TemplateAttributeResponseList(List<TemplateAttributeResponse> responseList) {
        this.responseList = responseList;
    }

    public List<TemplateAttributeResponse> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<TemplateAttributeResponse> responseList) {
        this.responseList = responseList;
    }
}
