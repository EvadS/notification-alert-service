package com.se.service.notification.service.notification;

import com.se.service.notification.model.NotificationModel;
import com.se.service.notification.model.enums.NotificationAlertType;

/**
 * Created by Evgeniy Skiba
 *
 * <title>Family of algorithms need for Strategy Pattern.</title>
 *
 * <p>Here is the interface for the Strategy Algorithm. Declares operations common to all
 * supported versions of some algorithm and identity each strategy using StrategyName defined as enum.
 * </p>
 */
public interface NotificationAlertsStrategy {
    /**
     * <p>Base algorithm for strategy
     * Send alert notification according to strategy type
     * </p>
     * @param notificationModel ase info about notification
     */
    void sendNotification(NotificationModel notificationModel);

    /**
     * identity each strategy
     *
     * @return StrategyNam
     */
    NotificationAlertType getStrategyName();
}
