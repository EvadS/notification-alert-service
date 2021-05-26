package com.se.service.notification.component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * Created by Evgeniy Skiba on 14.05.21
 */
@Component
public class SesMailSenderComponent {

    @Value("${ses.mail.address}")
    private String sesEmailFrom;


    // TODO: move to props file
    @Value("${amazon.access.key}")
    private String amazonAccessKey;
    @Value("${amazon.secret.key}")
    private String amazonSecretKey;
    @Value("${amazon.region}")
    private String amazonRegion;


    private static final Logger logger = LoggerFactory.getLogger(SesMailSenderComponent.class);

    public SesMailSenderComponent() {

    }

    public boolean sendHtml(String emailTo, String subject, String bodyHTML)
            throws MailFromDomainNotVerifiedException, MessageRejectedException {


        AmazonSimpleEmailService awsSes = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        amazonAccessKey, amazonSecretKey)))
                .withRegion(Regions.fromName(amazonRegion))
                .build();


        Content content = new  Content()
                .withCharset("UTF-8").withData(bodyHTML);

        Message message = new Message()
                .withBody(new Body().withHtml(content))
                .withSubject(new Content()
                        .withCharset(StandardCharsets.UTF_8.name()).withData(subject));

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(emailTo))
                .withMessage(message)
                .withSource(sesEmailFrom);


        SendEmailResult sendEmailResult = awsSes.sendEmail(request);
        logger.debug("Email with subject: {} has been sent. Message id: {}", subject, sendEmailResult.getMessageId());
        return !StringUtils.isEmpty(sendEmailResult.getMessageId());
    }
}
