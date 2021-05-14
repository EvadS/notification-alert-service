package com.se.service.notification.model.request;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evgeniy Skiba on 13.05.21
 */
public class UserGroupRequest {

    private final Set<String> templateAttribute = new HashSet<>();

    public UserGroupRequest() {
    }


    public Set<String> getTemplateAttribute() {
        return templateAttribute;
    }


    public Set<String> getTemplateAttribute(String attribute) {
        templateAttribute.add(attribute);
        return templateAttribute;
    }


}
