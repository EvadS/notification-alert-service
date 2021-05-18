package com.se.service.notification.model.enums;

import java.util.stream.Stream;

/**
 * Created by Evgeniy Skiba
 */
public enum NotificationAlertType {
    EMAIL(1, "email"),
    SMS (2, "sms"),
    PUSH_NOTIFICATION(3, "push notification");

    private  int id;
    private String name;

    NotificationAlertType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static NotificationAlertType of(int code) {
        return Stream.of(NotificationAlertType.values())
                .filter(p -> p.getId() == code)
                .findFirst().orElseThrow(
                        () -> new IllegalStateException(String.format("Unsupported type %s.", code)));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
