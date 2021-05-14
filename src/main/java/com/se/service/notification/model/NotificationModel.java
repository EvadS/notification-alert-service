package com.se.service.notification.model;

/**
 * Created by Evgeniy Skiba
 */
public class NotificationModel {

    private String recipient;
    private String subject;
    private String htmlBody;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", htmlBody='" + htmlBody + '\'' +
                '}';
    }
}
