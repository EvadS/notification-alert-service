package com.se.service.notification.service.notification;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.se.service.notification.component.SesMailSenderComponent;
import com.se.service.notification.component.SendGridMailerComponent;
import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.enums.NotificationAlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Evgeniy Skiba
 */
@Component
public class EmailAlertStrategy implements NotificationAlertsStrategy {

    @Value("${aws.ses.default.email}")
    private boolean isSesDefault;

    private final static Logger logger = LoggerFactory.getLogger(EmailAlertStrategy.class);
    private final SesMailSenderComponent sesMailSenderComponent;
    private final SendGridMailerComponent sendGridMailerComponent;
    private final AmazonSimpleEmailService emailService;

    public EmailAlertStrategy(SesMailSenderComponent sesMailSenderComponent, SendGridMailerComponent sendGridMailerComponent, AmazonSimpleEmailService emailService) {
        this.sesMailSenderComponent = sesMailSenderComponent;
        this.sendGridMailerComponent = sendGridMailerComponent;
        this.emailService = emailService;
    }


    @Override
    public void sendNotification(NotificationModel notificationModel) {

        try {
            if(isSesDefault){
                //send by ses
                sendBySes(notificationModel);
            }
            else {
                // send by send grid
                sendBySendGrid(notificationModel);
            }

        } catch (MailFromDomainNotVerifiedException e) {
            logger.error("MailFromDomainNotVerifiedException: {}", e.getMessage());
        } catch (MessageRejectedException ex) {
            logger.error("MessageRejectedException: {}", ex.getMessage());
        } catch ( IOException ex){
            String mailProvider = isSesDefault? "aws ses": "send grid";
            logger.error("Send email exception. Provider:{}, message:{}",mailProvider,ex.getLocalizedMessage() );
        }
        catch (Exception e) {
            logger.error("Bind template exception: {}. Email didn't send.", e.getMessage());
        }

    }

    private void sendBySendGrid(NotificationModel notificationModel) throws IOException {
        sendGridMailerComponent.sendHtml(notificationModel.getRecipient(),
                notificationModel.getSubject(), notificationModel.getHtmlBody());
        logger.debug("email sent to:{}", notificationModel.getRecipient());
    }

    private void sendBySes(NotificationModel notificationModel) {
        sesMailSenderComponent.sendHtml(notificationModel.getRecipient(),
                notificationModel.getSubject(), notificationModel.getHtmlBody());
        logger.debug("email sent to:{}", notificationModel.getRecipient());
    }

    @Override
    public NotificationAlertType getStrategyName() {
        return NotificationAlertType.EMAIL;
    }
}