package com.se.service.notification.model.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy Skiba on 13.05.21
 */
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
