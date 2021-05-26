package com.se.service.notification.service;

import com.se.service.notification.model.request.TemplateAttributeListRequest;
import com.se.service.notification.model.request.TemplateAttributeRequest;
import com.se.service.notification.model.response.TemplateAttributeResponse;
import com.se.service.notification.model.response.TemplateAttributeResponseList;

import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
public interface TemplateService {
    TemplateAttributeResponse getTemplateAttribute(Long id);

    TemplateAttributeResponse createTemplateAttribute(TemplateAttributeRequest templateAttributeRequest);

    TemplateAttributeResponse updateTemplateAttribute(Long id, TemplateAttributeRequest templateAttributeRequest);

    void deleteTemplateAttribute(Long id);

    TemplateAttributeResponseList availableAttributeList();

    TemplateAttributeResponseList createTemplateAttributeByList(TemplateAttributeListRequest templateAttributeRequest);
}
