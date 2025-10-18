package com.ven.taskboard.config;

import com.ven.taskboard.notify.adapter.ConsoleChannel;
import com.ven.taskboard.notify.adapter.EmailChannel;
import com.ven.taskboard.notify.adapter.SlackChannel;
import com.ven.taskboard.notify.bridge.NotificationChannel;
import com.ven.taskboard.notify.decorator.LoggingNotificationChannel;
import com.ven.taskboard.notify.decorator.RetryingNotificationChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Select a base channel, then decorate it with logging + retry.
@Configuration
public class NotificationConfig {

    @Bean
    NotificationChannel notificationChannel(
            @Value("${taskboard.notifications.channel:console}") String channelName,
            ConsoleChannel console, EmailChannel email, SlackChannel slack
    ) {
        NotificationChannel base = switch (channelName.toLowerCase()) {
            case "email" -> email;
            case "slack" -> slack;
            default -> console;
        };
        NotificationChannel logged = new LoggingNotificationChannel(base);
        return new RetryingNotificationChannel(logged, 2); //Decorator użycie
    }
}
