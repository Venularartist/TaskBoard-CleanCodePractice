package com.ven.taskboard.notify.adapter;

import com.ven.taskboard.notify.bridge.NotificationChannel;
import org.springframework.stereotype.Component;

// Adapter

// Implements the uniform NotificationChannel#send(to, subject, body)
// interface and adapts delivery to a console print (dev stub).

@Component
public class ConsoleChannel implements NotificationChannel {
    @Override
    public void send(String to, String subject, String body) {
        System.out.println("[CONSOLE] to=" + to + " subject=" + subject + " body=" + body);
    }
}
