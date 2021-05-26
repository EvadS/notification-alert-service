package com.se.service.notification.service.impl;

import com.se.service.notification.dao.entity.TemplateVariable;
import com.se.service.notification.dao.repository.TemplateVariablesRepository;
import com.se.service.notification.handler.exception.AlreadyExistException;
import com.se.service.notification.handler.exception.ResourceNotFoundException;
import com.se.service.notification.model.mapper.TemplateAttributeMapper;
import com.se.service.notification.model.request.TemplateAttributeListRequest;
import com.se.service.notification.model.request.TemplateAttributeRequest;
import com.se.service.notification.model.response.TemplateAttributeResponse;
import com.se.service.notification.model.response.TemplateAttributeResponseList;
import com.se.service.notification.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Evgeniy Skiba
 */
@Service
public class TemplateAttributeServiceImpl implements TemplateService {

    private static final Logger logger = LoggerFactory.getLogger(TemplateAttributeServiceImpl.class);


    private final TemplateVariablesRepository templateVariablesRepository;

    public TemplateAttributeServiceImpl(TemplateVariablesRepository templateVariablesRepository) {
        this.templateVariablesRepository = templateVariablesRepository;
    }

    @Override
    public TemplateAttributeResponse getTemplateAttribute(Long id) {
        TemplateVariable templateVariable = templateVariablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place holder", "id", id));

        return TemplateAttributeMapper.INSTANCE.templateVariableToResponse(templateVariable);
    }

    @Override
    public TemplateAttributeResponse createTemplateAttribute(TemplateAttributeRequest request) {

        if (templateVariablesRepository.existsByValue(request.getName())) {
            throw new AlreadyExistException("Place holder", "value", request.getName());
        }

        TemplateVariable templateVariable = new TemplateVariable(request.getName());

        templateVariablesRepository.save(templateVariable);
        return TemplateAttributeMapper.INSTANCE.templateVariableToResponse(templateVariable);
    }

    @Override
    public TemplateAttributeResponse updateTemplateAttribute(Long id, TemplateAttributeRequest request) {

        logger.debug("Update template attribute, id:{} request:{}", id, request);
        TemplateVariable templateVariable = templateVariablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place holder", "id", id));

        boolean alreadyExistsInDataBase = templateVariablesRepository.existsByValue(request.getName());
        boolean isChanged = !templateVariable.getValue().equals(request.getName());

        if (isChanged) {
            if (!alreadyExistsInDataBase) {
                templateVariable.setValue(request.getName());
                templateVariablesRepository.save(templateVariable);

            } else {
                throw new AlreadyExistException("Place holder", "value", request.getName());
            }
        }

        return TemplateAttributeMapper.INSTANCE.templateVariableToResponse(templateVariable);
    }

    @Override
    public void deleteTemplateAttribute(Long id) {
        logger.debug("Delete template attribute, id:{} ", id);

        TemplateVariable templateVariable = templateVariablesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place holder", "id", id));

        templateVariablesRepository.delete(templateVariable);
    }

    @Override
    public TemplateAttributeResponseList availableAttributeList() {
        TemplateAttributeResponseList templateAttributeResponseList = new TemplateAttributeResponseList();

        List<TemplateAttributeResponse> attributeResponseList = templateVariablesRepository.findAll().stream()
                .map(TemplateAttributeMapper.INSTANCE::templateVariableToResponse)
                .collect(Collectors.toList());

        templateAttributeResponseList.setResponseList(attributeResponseList);

        return templateAttributeResponseList;

    }

    @Override
    public TemplateAttributeResponseList createTemplateAttributeByList(TemplateAttributeListRequest templateAttributeRequest) {

        List<TemplateAttributeResponse> responseList = new ArrayList<>();

        templateAttributeRequest.getAttributeRequestList().stream().forEach(it -> {
            try {
                TemplateAttributeResponse attributeResponse = createTemplateAttribute(it);
                responseList.add(attributeResponse);
            } catch (Exception ex) {
                // skip incorrect param
                logger.error(ex.getLocalizedMessage());
            }
        });

        return new TemplateAttributeResponseList(responseList);
    }
}
