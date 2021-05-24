package com.se.service.notification.dao.entity;

import com.se.service.notification.dao.entity.common.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */
@Entity(name = "Notification")
@Table(name = "notification")
public class Notification extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name cannot be null or empty ")
    @Column(name = "name", columnDefinition = "text")
    private String name;

    @NotNull
    @Column(name = "enabled",
            columnDefinition =  "boolean default true",
            nullable = false)
    public boolean enabled = true;

    @Column(name = "html_content", columnDefinition = "text")
    private String htmlPart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Notification_group_id")
    private NotificationGroup notificationGroup;

    public Notification() {
    }

    public Notification(@NotBlank(message = "Name cannot be null or empty ") String name,
                        @NotNull boolean enabled, String htmlPart, NotificationGroup notificationGroup) {
        this.name = name;
        this.enabled = enabled;
        this.htmlPart = htmlPart;
        this.notificationGroup = notificationGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHtmlPart() {
        return htmlPart;
    }

    public void setHtmlPart(String htmlPart) {
        this.htmlPart = htmlPart;
    }

    public NotificationGroup getNotificationGroup() {
        return notificationGroup;
    }

    public void setNotificationGroup(NotificationGroup notificationGroup) {
        this.notificationGroup = notificationGroup;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", htmlPart='" + htmlPart + '\'' +
                '}';
    }
}
