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
public class NotificationServiceApplication  implements CommandLineRunner {

    @Autowired
    SendGridMailer sendGridMailer;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            SendGrid sg = new SendGrid(System.getenv("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"));
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"test@example.com\"}],\"subject\":\"Sending with Twilio SendGrid is Fun\"}],\"from\":{\"email\":\"test@example.com\"},\"content\":[{\"type\":\" text/html;\",\"value\": \"and easy to do anywhere, even with Java\"}]}");
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        } int a =0;
    }
}
