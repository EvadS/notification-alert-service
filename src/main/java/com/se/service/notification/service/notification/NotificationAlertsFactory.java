package com.se.service.notification.service.notification;

import com.se.service.notification.model.enums.NotificationAlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by Evgeniy Skiba
 *
 * <p>Inject all the strategies in the factory. Here we are storing the strategies in a Map while
 * constructing StrategyFactory, this makes strategy lookup O(1).
 * </p>
 */
@Component
public class NotificationAlertsFactory {
    private Map<NotificationAlertType, NotificationAlertsStrategy> strategies;


    public NotificationAlertsFactory(Set<NotificationAlertsStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public NotificationAlertsStrategy findStrategy(NotificationAlertType templateStrategyType) {
        NotificationAlertsStrategy notificationAlertsStrategy =  strategies.get(templateStrategyType);
        return  notificationAlertsStrategy;
    }

     private void createStrategy(Set<NotificationAlertsStrategy> strategySet) {
        strategies = new HashMap <NotificationAlertType, NotificationAlertsStrategy>();

        strategySet.forEach(
                strategy ->strategies.put(strategy.getStrategyName(), strategy));
    }
}