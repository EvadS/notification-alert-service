package com.se.service.notification.service.notification;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.enums.NotificationAlertType;


import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by Evgeniy Skiba
 */
@Component
public class SmsAlertStrategy  implements  NotificationAlertsStrategy{

    private final static Logger logger = LoggerFactory.getLogger(SmsAlertStrategy.class);


    @Override
    public void sendNotification(NotificationModel notificationModel)
    {
        logger.info("Send sms");
        AWSCredentialsProvider awsCreds = new ClasspathPropertiesFileCredentialsProvider();
        AWSCredentials credentials = awsCreds.getCredentials();

        //TODO: try to get region from EC2,
        Region currentRegion = Regions.getCurrentRegion();
        if (currentRegion == null) {
            currentRegion = Region.getRegion(Regions.US_WEST_2);
        }


        //--------------------------------------------------------------------
        AmazonSNSClient snsClient = new AmazonSNSClient(credentials);
        String message = notificationModel.getHtmlBody();
        String phoneNumber = notificationModel.getRecipient();
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        //<set SMS attributes>
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);


    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
    }



    @Override
    public NotificationAlertType getStrategyName() {
        return NotificationAlertType.SMS;
    }
}
