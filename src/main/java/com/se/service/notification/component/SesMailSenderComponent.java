package com.se.service.notification.component;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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


   private final AmazonSimpleEmailService awsSesComponent;

    private static final Logger logger = LoggerFactory.getLogger(SesMailSenderComponent.class);

    public SesMailSenderComponent(AmazonSimpleEmailService awsSes) {
        this.awsSesComponent = awsSes;
    }

    public boolean sendHtml(String emailTo, String subject, String bodyHTML)
            throws MailFromDomainNotVerifiedException, MessageRejectedException {

        //************************************
       // AWSCredentialsProvider awsCreds = new ClasspathPropertiesFileCredentialsProvider();

     //   AWSCredentials credentials = awsCreds.getCredentials();

        //TODO: try to get region from EC2,

//        AmazonSimpleEmailService awsSes = AmazonSimpleEmailServiceClientBuilder.standard()
//                .withCredentials(
//                        new AWSStaticCredentialsProvider(
//                                new BasicAWSCredentials(
//                                        credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey())))
//                                        //  amazonAccessKey, amazonSecretKey)))
//                .withRegion(String.valueOf(currentRegion))
//                .build();
        //********************************


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


        SendEmailResult sendEmailResult = awsSesComponent.sendEmail(request);
        logger.debug("Email with subject: {} has been sent. Message id: {}", subject, sendEmailResult.getMessageId());
        return true;
    }
}
