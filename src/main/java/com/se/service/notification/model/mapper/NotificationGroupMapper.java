package com.se.service.notification.model.mapper;

import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.model.response.NotificationGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by Evgeniy Skiba
 */
@Mapper
public interface NotificationGroupMapper {

    NotificationGroupMapper INSTANCE = Mappers.getMapper(NotificationGroupMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "status", source = "enabled")
    })
    NotificationGroupResponse notificationGroupToResponse(NotificationGroup notificationGroup);
}
