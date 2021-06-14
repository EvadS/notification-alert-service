package com.se.service.notification.service.impl;

import com.se.service.notification.dao.entity.Notification;
import com.se.service.notification.dao.entity.NotificationGroup;
import com.se.service.notification.dao.repository.NotificationGroupRepository;
import com.se.service.notification.dao.repository.NotificationRepository;
import com.se.service.notification.dao.repository.NotificationTypeRepository;
import com.se.service.notification.handler.exception.AlreadyExistException;
import com.se.service.notification.handler.exception.IncorrectTemplateException;
import com.se.service.notification.handler.exception.NotificationGroupException;
import com.se.service.notification.handler.exception.ResourceNotFoundException;
import com.se.service.notification.model.mapper.NotificationGroupMapper;
import com.se.service.notification.model.mapper.NotificationItemTypeMapper;
import com.se.service.notification.model.mapper.NotificationMapper;
import com.se.service.notification.model.request.NotificationGroupRequest;
import com.se.service.notification.model.request.NotificationRequest;
import com.se.service.notification.model.request.NotificationTypeRequest;
import com.se.service.notification.model.response.*;
import com.se.service.notification.service.NotificationItemTypeService;
import com.se.service.notification.service.NotificationService;
import com.se.service.notification.service.TemplateBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evgeniy Skiba
 */

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    public static final String NOTIFICATION_GROUP = "Notification group";

    private final NotificationGroupRepository notificationGroupRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;

    private final TemplateBuilderService templateService;
    private final NotificationItemTypeService notificationItemTypeService;

    public NotificationServiceImpl(NotificationGroupRepository notificationGroupRepository,
                                   NotificationRepository notificationRepository,
                                   NotificationTypeRepository notificationTypeRepository,
                                   TemplateBuilderService templateService, NotificationItemTypeService notificationItemTypeService) {
        this.notificationGroupRepository = notificationGroupRepository;
        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
        this.templateService = templateService;
        this.notificationItemTypeService = notificationItemTypeService;
    }

    @Override
    public NotificationGroupResponse createNotificationGroup(NotificationGroupRequest request) {

        logger.debug("Handle create notification Group request: {}", request);

        if (notificationGroupRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("Notification Group", "name", request.getName());
        }

        NotificationGroup notificationGroup = new NotificationGroup(
                request.getName(), request.isEnabled());

        notificationGroupRepository.save(notificationGroup);

        return NotificationGroupMapper.INSTANCE.notificationGroupToResponse(notificationGroup);
    }

    @Override
    public NotificationGroupResponse updateNotificationGroup(long id, NotificationGroupRequest request) {

        logger.debug("Handle update notification Group, id:{},  request:{}", id, request);

        NotificationGroup notificationGroup = notificationGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION_GROUP, "id", id));


        boolean alreadyExistsInDataBase =notificationGroupRepository.existsByName(request.getName());
        boolean isChanged = !notificationGroup.getName().equals(request.getName());

        if (isChanged) {
            if (!alreadyExistsInDataBase) {
                notificationGroup.setName(request.getName());
                notificationGroup.setEnabled(request.isEnabled());

                notificationGroupRepository.save(notificationGroup);

            } else {
                throw new AlreadyExistException("Notification group", "name", request.getName());
            }
        }

        return NotificationGroupMapper.INSTANCE.notificationGroupToResponse(notificationGroup);
    }

    @Override
    public void deleteNotificationGroup(long id) {
        logger.debug("Handle delete  notification Group, id:{}", id);

        NotificationGroup notificationGroup = notificationGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification group", "id", id));

        // can't delete when exists child
        List<Notification> notificationList = notificationRepository.findAllByNotificationGroup(notificationGroup);

        if (!notificationList.isEmpty())
            throw new NotificationGroupException(notificationGroup.getName());

        notificationGroupRepository.delete(notificationGroup);
    }

    @Override
    public NotificationGroupResponse changeNotificationGroupStatus(long id, boolean status) {
        logger.debug("Handle change notification group id:{} to status: {}", id, status);
        NotificationGroup notificationGroup = notificationGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification group", "id", id));

        notificationGroup.setEnabled(status);

        // disabled all children
        List<Notification> notificationList = notificationRepository.findAllByNotificationGroup(notificationGroup);

        for (Notification item : notificationList) {
            item.setEnabled(status);
            notificationRepository.save(item);
        }

        return NotificationGroupMapper.INSTANCE.notificationGroupToResponse(notificationGroup);
    }

    @Override
    public List<NotificationGroupResponse> list() {
        return notificationGroupRepository.findAll()
                .stream()
                .map(NotificationGroupMapper.INSTANCE::notificationGroupToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public NotificationBaseResponse createNotificationItem(NotificationRequest request) {
        if (notificationGroupRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("Notification", "name", request.getName());
        }

        NotificationGroup notificationGroup = notificationGroupRepository.getOne(request.getParentGroupId());

        if (!templateService.isTemplateValid(request.getHtmlContent())) {
            throw new IncorrectTemplateException("Html content has incorrect expresion.");
        }

        Notification notification = new Notification(request.getName(), request.isEnabled(),
                request.getHtmlContent(), notificationGroup);
        notificationRepository.save(notification);

        notificationItemTypeService.createDefaultNotificationTypesByItem(notification);

        return NotificationMapper.INSTANCE.notificationItemToNotificationBaseResponse(notification);
    }

    @Override
    public NotificationBaseResponse updateNotificationItem(long id, NotificationRequest request) {

        logger.debug("Handle update notification id:{}", id);

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));


        boolean alreadyExistsInDataBase = notificationRepository.existsByName(request.getName());
        boolean isChanged = !notification.getName().equals(request.getName());

        if (isChanged) {
            if (!alreadyExistsInDataBase) {
                notification.setName(request.getName());
                notification.setHtmlPart(request.getHtmlContent());
                notification.setEnabled(request.isEnabled());

                notificationRepository.save(notification);

            } else {
                throw new AlreadyExistException("Notification group", "name", request.getName());
            }
        }

        return NotificationMapper.INSTANCE.notificationItemToNotificationBaseResponse(notification);
    }

    @Override
    public void deleteNotificationItem(long id) {

    }

    @Override
    public void changeNotificationItemStatus(long id, boolean status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification item", "id", id));

        notification.setEnabled(status);
        notificationRepository.save(notification);
    }

    @Override
    public NotificationGroupResponse getGroupInfo(Long id) {
        NotificationGroup notificationGroup = notificationGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification group", "id", id));

        return NotificationGroupMapper.INSTANCE.notificationGroupToResponse(notificationGroup);
    }

    @Override
    public NotificationResponse getNotificationItem(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification item", "id", id));

        Set<String> templatePlaceHolders =  templateService.getTemplateVariables(notification.getHtmlPart());
        return  NotificationMapper.INSTANCE.notificationItemToResponse(notification, templatePlaceHolders);
    }

    @Override
    public Page<NotificationResponse> pagedNotificationResponse(Pageable pageable) {

        List<NotificationResponse> notificationResponseList =
                notificationRepository.findAll(pageable).stream()
               .map(it -> {

                   Set<String> templatePlaceHolders =  templateService.getTemplateVariables(it.getHtmlPart());
                    return NotificationMapper.INSTANCE.notificationItemToResponse(it, templatePlaceHolders);
               }).collect(Collectors.toList());

        return new PageImpl<>(notificationResponseList, pageable, notificationResponseList.size());

    }

    @Override
    public NotificationItemTypeResponse getNotificationAlertTypes(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification Item", "id", notificationId));

        List<NotificationItemTypeItemResponse> notificationItemTypeItemResponseList =
                notificationTypeRepository.findAllByNotification(notification)
                        .stream()
                        .map(NotificationItemTypeMapper.INSTANCE::notificationItemTypeToResponse)
                        .collect(Collectors.toList());

        NotificationItemTypeResponse notificationItemTypeResponse = new NotificationItemTypeResponse();
        notificationItemTypeResponse.setNotificationItemTypeItemResponseList(notificationItemTypeItemResponseList);
        notificationItemTypeResponse.setNotificationItemId(notification.getId());


        return notificationItemTypeResponse;
    }

    @Override
    public NotificationItemTypeResponse setNotificationAlertTypes(NotificationTypeRequest notificationRequest) {
        Notification notification = notificationRepository.findById(notificationRequest.getNotificationItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Notification Item", "id", notificationRequest.getNotificationItemId()));


        NotificationItemTypeResponse notificationItemTypeResponse = notificationItemTypeService.changeNotificationAlertStatus(notification, notificationRequest.getNotificationTypeItemRequests());
        return null;
    }
}
