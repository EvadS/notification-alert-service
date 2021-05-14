package com.se.service.notification.service;

import java.util.Map;
import java.util.Set;

/**
 * Created by Evgeniy Skiba
 */
public interface TemplateBuilderService {

    String bindTemplate(String templateBody, Map<String, String> templateModel);

    /**
     * Find all the variables used in the Freemarker Template
     *
     * @param templateBody template body
     * @return
     */
    Set<String> getTemplateVariables(String templateBody);

    boolean isTemplateValid(String templateBody);

}
