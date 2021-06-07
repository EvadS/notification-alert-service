package com.se.service.notification.component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.se.service.notification.configuration.AwsConfiguration;
import com.se.service.notification.configuration.NotificationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * Created by Evgeniy Skiba on 14.05.21
 */
@Component
public class SesMailSenderComponent {
    private static final Logger logger = LoggerFactory.getLogger(SesMailSenderComponent.class);
    private final AwsConfiguration awsConfiguration;
    private  final NotificationProperties notificationProperties;


    public SesMailSenderComponent(AwsConfiguration awsConfiguration, NotificationProperties notificationProperties) {
        this.awsConfiguration = awsConfiguration;
        this.notificationProperties = notificationProperties;
    }

    public boolean sendHtml(String emailTo, String subject, String bodyHTML) {

        AmazonSimpleEmailService awsSes = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        awsConfiguration.getAccessKey(),
                                        awsConfiguration.getSecretKey()
                        )))
                .withRegion(Regions.fromName(awsConfiguration.getRegionStatic()))
                .build();


        Content content = new Content()
                .withCharset(StandardCharsets.UTF_8.name()).withData(bodyHTML);

        Message message = new Message()
                .withBody(new Body().withHtml(content))
                .withSubject(new Content()
                        .withCharset(StandardCharsets.UTF_8.name()).withData(subject));

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(emailTo))
                .withMessage(message)
                .withSource(notificationProperties.getSender());

        SendEmailResult sendEmailResult = awsSes.sendEmail(request);
        logger.debug("Email with subject: {} has been sent. Message id: {}", subject, sendEmailResult.getMessageId());
        return !StringUtils.isEmpty(sendEmailResult.getMessageId());
    }
}
