package com.se.service.notification.component;

import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.se.service.notification.model.NotificationModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeniy Skiba
 */
@Component
public class SnsSenderComponent {

    private final AmazonSNSClient awsSnsClient;

    public SnsSenderComponent(AmazonSNSClient awsSnsClient) {
        this.awsSnsClient = awsSnsClient;
    }


    public boolean sendNotification(NotificationModel notificationModel)
            throws MailFromDomainNotVerifiedException, MessageRejectedException {

        String message = notificationModel.getHtmlBody();
        String phoneNumber = notificationModel.getRecipient();
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();

        PublishResult result = awsSnsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));

        return true;
    }
}
