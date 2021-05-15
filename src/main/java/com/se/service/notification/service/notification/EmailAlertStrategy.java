package com.se.service.notification.service.notification;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.se.service.notification.component.MailSenderComponent;
import com.se.service.notification.component.SendGridMailer;
import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.enums.NotificationAlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Evgeniy Skiba
 */
@Component
public class EmailAlertStrategy implements NotificationAlertsStrategy {

    @Value("${aws.ses.default.email}")
    private boolean isSesDefault;

    private final static Logger logger = LoggerFactory.getLogger(EmailAlertStrategy.class);
    private final MailSenderComponent mailSenderComponent;
    private final SendGridMailer sendGridMailer;
    private final AmazonSimpleEmailService emailService;

    public EmailAlertStrategy(MailSenderComponent mailSenderComponent, SendGridMailer sendGridMailer, AmazonSimpleEmailService emailService) {
        this.mailSenderComponent = mailSenderComponent;
        this.sendGridMailer = sendGridMailer;
        this.emailService = emailService;
    }


    @Override
    public void sendNotification(NotificationModel notificationModel) {

        if(isSesDefault){
            //send by ses
            sendBySes(notificationModel);
        }
        else {
            // send by send grid
            sendBySendGrid(notificationModel);
        }
    }

    private void sendBySendGrid(NotificationModel notificationModel) {

        try {
            mailSenderComponent.sendHtml(notificationModel.getRecipient(),
                    notificationModel.getSubject(), notificationModel.getHtmlBody());
            logger.debug("email sent to:{}", notificationModel.getRecipient());

        } catch (MailFromDomainNotVerifiedException e) {
            logger.error("MailFromDomainNotVerifiedException: {}", e.getMessage());
        } catch (MessageRejectedException ex) {
            logger.error("MessageRejectedException: {}", ex.getMessage());
        } catch (Exception e) {
            logger.error("Bind template exception: {}. Email didn't send.", e.getMessage());
        }
    }

    private void sendBySes(NotificationModel notificationModel) {
        try {
            sendGridMailer.sendHtml(notificationModel.getRecipient(),
                    notificationModel.getSubject(), notificationModel.getHtmlBody());
            logger.debug("email sent to:{}", notificationModel.getRecipient());

        } catch (MailFromDomainNotVerifiedException e) {
            logger.error("MailFromDomainNotVerifiedException: {}", e.getMessage());
        } catch (MessageRejectedException ex) {
            logger.error("MessageRejectedException: {}", ex.getMessage());
        } catch (Exception e) {
            logger.error("Bind template exception: {}. Email didn't send.", e.getMessage());
        }
    }

    @Override
    public NotificationAlertType getStrategyName() {
        return NotificationAlertType.EMAIL;
    }
}