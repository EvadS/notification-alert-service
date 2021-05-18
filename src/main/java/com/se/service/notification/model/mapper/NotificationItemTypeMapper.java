package com.se.service.notification.model.mapper;

import com.se.service.notification.dao.entity.NotificationType;
import com.se.service.notification.model.response.NotificationItemTypeItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by Evgeniy Skiba
 */
@Mapper
public interface NotificationItemTypeMapper {

    NotificationItemTypeMapper INSTANCE = Mappers.getMapper(NotificationItemTypeMapper.class);

    @Mappings({
            @Mapping(target = "alertType", source = "notificationAlertType"),
            @Mapping(target = "enabled", source = "enabled")
    })
    NotificationItemTypeItemResponse notificationItemTypeToResponse(NotificationType notificationType);
}
