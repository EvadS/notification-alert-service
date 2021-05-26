package com.se.service.notification;

import com.se.service.notification.configuration.NotificationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Evgeniy Skiba
 */
@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
        NotificationProperties.class
})
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
