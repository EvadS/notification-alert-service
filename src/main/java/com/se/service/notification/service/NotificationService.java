package com.se.service.notification.service;

import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.request.NotificationRequest;
import com.se.service.notification.model.request.NotificationTypeRequest;
import com.se.service.notification.model.response.NotificationBaseResponse;
import com.se.service.notification.model.response.NotificationGroupResponse;
import com.se.service.notification.model.response.NotificationItemTypeResponse;
import com.se.service.notification.model.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Evgeniy Skiba
 */
public interface NotificationService {

    NotificationGroupResponse createNotificationGroup(NotificationGroupRequest request);

    NotificationGroupResponse updateNotificationGroup(long id, NotificationGroupRequest request);

    void deleteNotificationGroup(long id);

    NotificationGroupResponse changeNotificationGroupStatus(long id, boolean status);

    List<NotificationGroupResponse> list();

    NotificationBaseResponse createNotificationItem(NotificationRequest request);

    NotificationBaseResponse updateNotificationItem(long id, NotificationRequest request);

    void deleteNotificationItem(long id);

    void changeNotificationItemStatus(long id, boolean status);

    //List<NotificationResponse> notificationItemByParent(long parentId);

    NotificationGroupResponse getGroupInfo(Long id);

    NotificationResponse getNotificationItem(Long id);

    Page<NotificationResponse> pagedNotificationResponse(Pageable pageable);

    NotificationItemTypeResponse getNotificationAlertTypes(Long notificationId);

    NotificationItemTypeResponse setNotificationAlertTypes(NotificationTypeRequest notificationRequest);
}
