package com.ven.taskboard.notify.adapter;

import com.ven.taskboard.notify.bridge.NotificationChannel;
import org.springframework.stereotype.Component;

// Adapter — would wrap JavaMailSender; dev default prints to console.
@Component
public class EmailChannel implements NotificationChannel {
    @Override
    public void send(String to, String subject, String body) {
        System.out.println("[EMAIL] to=" + to + " subject=" + subject + " body=" + body);
    }
}
