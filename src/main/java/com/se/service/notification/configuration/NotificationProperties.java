package com.se.service.notification.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * Created by Evgeniy Skiba
 */

@Configuration
@ConfigurationProperties(prefix = "notification.sp")
@Validated
public class NotificationProperties {
    private String sender;

    @NotNull
    private boolean sesDefault;

    public NotificationProperties() {
    }

    public boolean isSesDefault() {
        return sesDefault;
    }

    public void setSesDefault(boolean sesDefault) {
        this.sesDefault = sesDefault;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
