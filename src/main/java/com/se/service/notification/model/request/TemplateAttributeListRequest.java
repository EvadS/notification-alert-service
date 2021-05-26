package com.se.service.notification.model.request;

import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba on 13.05.21
 */
@ApiModel(value = "TemplateAttributeListRequest",
        description = "Provide list of template attribute")
public class TemplateAttributeListRequest {

    private List<TemplateAttributeRequest> attributeRequestList = new ArrayList<>();

    public TemplateAttributeListRequest() {
    }

    public List<TemplateAttributeRequest> getAttributeRequestList() {
        return attributeRequestList;
    }

    public void setAttributeRequestList(List<TemplateAttributeRequest> attributeRequestList) {
        this.attributeRequestList = attributeRequestList;
    }
}
