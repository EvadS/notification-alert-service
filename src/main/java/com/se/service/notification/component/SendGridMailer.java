package com.se.service.notification.component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendGridMailer {

    private final static Logger logger = LoggerFactory.getLogger(SendGridMailer.class);
    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    public void sendMail() {
        Email from = new Email("evad.se.dev@gmail.com");
        String subject = "Sending with Twilio SendGrid is Fun";
        Email to = new Email("evad.se.dev@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv(apiKey));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            //logger.error()
            //TODO: logger
            ex.printStackTrace();

        }
    }

    public void sendHtml(String recipient, String subject, String htmlBody) throws IOException {
        Email from = new Email("no-reply@keabank.com");

        Email to = new Email(recipient);
        Content content = new Content("text/html", htmlBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

        logger.info("Status code:{}, response body:{}",
                response.getStatusCode(), response.getBody());
    }
}