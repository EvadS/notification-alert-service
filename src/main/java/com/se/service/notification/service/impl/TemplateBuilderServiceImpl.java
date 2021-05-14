package com.se.service.notification.service.impl;

import com.se.service.notification.dao.entity.TemplateVariable;
import com.se.service.notification.dao.repository.TemplateVariablesRepository;
import com.se.service.notification.handler.exception.BindTemplateException;
import com.se.service.notification.handler.exception.NotFoundException;
import com.se.service.notification.service.TemplateBuilderService;
import freemarker.core.InvalidReferenceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evgeniy Skiba
 */
@Service
public class TemplateBuilderServiceImpl implements TemplateBuilderService {

    private final static Logger logger = LoggerFactory.getLogger(TemplateBuilderServiceImpl.class);

    private final Configuration freemarkerConfiguration;
    private final TemplateVariablesRepository templateVariablesRepository;

    public TemplateBuilderServiceImpl(Configuration freemarkerConfiguration, TemplateVariablesRepository templateVariablesRepository) {
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.templateVariablesRepository = templateVariablesRepository;
    }



    @Override
    public String bindTemplate(String templateBody, Map<String, String> templateModel) {
        try {
            Template template = new Template("templateName", new StringReader(templateBody), freemarkerConfiguration);
            String bindTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateModel);

            return bindTemplate;
        } catch (IOException e) {
            // SkiEA  this situation checked before when we got template from data base
            throw new NotFoundException(e.getLocalizedMessage());
        } catch (TemplateException e) {
            throw new BindTemplateException(e.getBlamedExpressionString());
        }
    }
    @Override
    public Set<String> getTemplateVariables(String templateBody) {

        Template template = null;
        try {
            template = new Template("templateName", new StringReader(templateBody),
                    freemarkerConfiguration);
        } catch (IOException ioException) {
            // SkiEa doesn't happen cause use template from data base - it handled before
            logger.error("Template IOException: {}", ioException.getLocalizedMessage());
            return new HashSet<String>();
        }

        // Template template = getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> dataModel = new HashMap<>();
        boolean exceptionCaught;

        do {
            exceptionCaught = false;
            try {
                template.process(dataModel, stringWriter);
            } catch (InvalidReferenceException e) {
                exceptionCaught = true;
                dataModel.put(e.getBlamedExpressionString(), "");
            } catch (IOException | TemplateException e) {
                throw new IllegalStateException("Failed to Load Template: " + "templateName", e);
            }
        } while (exceptionCaught);

        return dataModel.keySet();
    }

    @Override
    public boolean isTemplateValid(String templateBody) {

        //Template variables in body
        Set<String> templateExpressionSet = getTemplateVariables(templateBody);

        logger.info("Template has:{} expressions.",templateExpressionSet.size());
        if (templateExpressionSet.isEmpty()) {
            // Nothing to check
            return true;
        }

       logger.info("The current template expressions:[{}] ",String.join(",", templateExpressionSet));

        Set<String> availableExpressions = templateVariablesRepository.findAll().stream()
                .map(TemplateVariable::getValue)
                .collect(Collectors.toSet());

        //TODO: Get list with unsupported attributes
        return availableExpressions.containsAll(templateExpressionSet);
    }
}
