package com.se.service.notification.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Evgeniy Skiba
 */
@Entity
@Table(name = "notification_group")
public class NotificationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank
    @Column(name = "name", columnDefinition = "text")
    private String name;

    @NotNull
    @Column(
            name = "enabled",
            columnDefinition =  "boolean default true",
            nullable = false)
    public boolean enabled = true;

    public NotificationGroup() {
    }

    public NotificationGroup(@NotBlank String name, @NotNull boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
