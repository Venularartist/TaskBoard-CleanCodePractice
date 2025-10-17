package com.ven.taskboard.notify.adapter;

import com.ven.taskboard.notify.bridge.NotificationChannel;
import org.springframework.stereotype.Component;

// Adapter — would call Slack Webhook; dev default prints to console.
@Component
public class SlackChannel implements NotificationChannel {
    @Override
    public void send(String to, String subject, String body) {
        System.out.println("[SLACK] to=" + to + " subject=" + subject + " body=" + body);
    }
}
