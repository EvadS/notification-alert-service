package com.se.service.notification.model.mapper;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.model.response.NotificationBaseResponse;
import com.se.service.notification.model.response.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Set;

/**
 * Created by Evgeniy Skiba
 */
@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "notification.id"),
            @Mapping(target = "name", source = "notification.name"),
            @Mapping(target = "enabled", source = "notification.enabled"),
            @Mapping(target = "placeHoldersList", source = "placeHoList")
    })
    NotificationResponse notificationItemToResponse(Notification notification, Set<String> placeHoList);


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "htmlContent", source = "htmlPart"),
            @Mapping(target = "enabled", source = "enabled"),
            @Mapping(target = "parentGroupId", ignore = true)
    })
    NotificationBaseResponse notificationItemToNotificationBaseResponse(Notification notification);


}
