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
import software.amazon.awssdk.http.HttpStatusCode;

import java.io.IOException;

@Component
public class SendGridMailerComponent {

    private final static Logger logger = LoggerFactory.getLogger(SendGridMailerComponent.class);

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    @Value("${send-grid.mail.from}")
    private String sendGridEmailFrom;


    public boolean sendHtml(String recipient, String subject, String htmlBody) throws IOException {
        Email from = new Email(sendGridEmailFrom);

        Email to = new Email(recipient);
        Content content = new Content("text/html", htmlBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

        logger.debug("Status code:{}, response body:{}",
                response.getStatusCode(), response.getBody());

        return  response.getStatusCode()==(HttpStatusCode.ACCEPTED);
//        return  response.getStatusCode();
       // return true;
    }
}