package com.se.service.notification.dao.entity;

import com.se.service.notification.model.converter.NotificationAlertTypeConverter;
import com.se.service.notification.model.enums.NotificationAlertType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */
@Entity(name = "NotificationType")
@Table(name = "notification_type")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Convert(converter = NotificationAlertTypeConverter.class)
    @Column(name = "notification_alert_type", nullable = false)
    private NotificationAlertType notificationAlertType;

    @NotNull
    @Column(name = "enabled",
            columnDefinition =  "boolean default true",
            nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    public NotificationType() {
    }

    public NotificationType(@NotNull NotificationAlertType notificationAlertType, Notification notification) {
        this.notificationAlertType = notificationAlertType;
        this.notification = notification;
        this.enabled = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationAlertType getNotificationAlertType() {
        return notificationAlertType;
    }

    public void setNotificationAlertType(NotificationAlertType notificationAlertType) {
        this.notificationAlertType = notificationAlertType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
