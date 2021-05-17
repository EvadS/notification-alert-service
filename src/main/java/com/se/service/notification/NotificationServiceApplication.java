package com.se.service.notification;

import com.se.service.notification.component.SendGridMailer;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

/**
 * Created by Evgeniy Skiba
 */
@EnableJpaAuditing
@SpringBootApplication
public class NotificationServiceApplication  {


    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

}
