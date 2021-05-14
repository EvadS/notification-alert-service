package com.se.service.notification.model.mapper;

import com.se.service.notification.dao.entity.TemplateVariable;
import com.se.service.notification.model.response.TemplateAttributeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by Evgeniy Skiba
 */

@Mapper
public interface TemplateAttributeMapper {

    TemplateAttributeMapper INSTANCE = Mappers.getMapper(TemplateAttributeMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "value")
    })
    TemplateAttributeResponse templateVariableToResponse(TemplateVariable templateVariable);
}
