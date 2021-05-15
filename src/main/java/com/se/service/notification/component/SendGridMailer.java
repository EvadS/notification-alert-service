package com.se.service.notification.component;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendGridMailer {

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

   public  void sendMail() {
//        Email from = new Email("from@example.com", "Foo Bar");
//        String subject = "Test email with SendGrid";
//        Email to = new Email("to@example.com");
//        Content content = new Content("text/plain", "Test email with Spring");
//        Mail mail = new Mail(from, subject, to, content);
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sendGridAPI.api(request);
//            System.out.println(response.getBody());
//        } catch (IOException ex) {
//            //TODO: logger error
//            ex.printStackTrace();
//        }

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

    // TODO: html istead of text
    public void sendHtml(String recipient, String subject, String htmlBody) {
        Email from = new Email("evad.se.dev@gmail.com");

        Email to = new Email(recipient);
        Content content = new Content("text/html;", htmlBody);
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
}