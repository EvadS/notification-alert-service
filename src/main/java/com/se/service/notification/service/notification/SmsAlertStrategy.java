package com.se.service.notification.service.notification;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.se.service.notification.component.SnsSenderComponent;
import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.enums.NotificationAlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by Evgeniy Skiba
 */
@Component
public class SmsAlertStrategy implements NotificationAlertsStrategy {

    public static final String SMS_NOTIFICATION_ERROR = "sms notification error";
    private final static Logger logger = LoggerFactory.getLogger(SmsAlertStrategy.class);
    private final SnsSenderComponent snsSenderComponent;

    public SmsAlertStrategy(SnsSenderComponent snsSenderComponent) {
        this.snsSenderComponent = snsSenderComponent;
    }

    @Override
    public void sendNotification(NotificationModel notificationModel) {
        logger.info("Send sms");
        AWSCredentialsProvider awsCreds = new ClasspathPropertiesFileCredentialsProvider();
        AWSCredentials credentials = awsCreds.getCredentials();

        try {
            snsSenderComponent.sendNotification(notificationModel);
        } catch (Exception e) {
            logger.error(SMS_NOTIFICATION_ERROR, e.getLocalizedMessage());
        }

    }


    @Override
    public NotificationAlertType getStrategyName() {
        return NotificationAlertType.SMS;
    }
}
