package com.se.service.notification.service.notification;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.se.service.notification.component.MailSenderComponent;
import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.enums.NotificationAlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Evgeniy Skiba
 */
@Component
public class EmailAlertStrategy implements NotificationAlertsStrategy {

    private final static Logger logger = LoggerFactory.getLogger(EmailAlertStrategy.class);
    private final MailSenderComponent mailSenderComponent;

    private final AmazonSimpleEmailService emailService;

    public EmailAlertStrategy(MailSenderComponent mailSenderComponent, AmazonSimpleEmailService emailService) {
        this.mailSenderComponent = mailSenderComponent;
        this.emailService = emailService;
    }


    @Override
    public void sendNotification(NotificationModel notificationModel) {

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

    @Override
    public NotificationAlertType getStrategyName() {
        return NotificationAlertType.EMAIL;
    }
}