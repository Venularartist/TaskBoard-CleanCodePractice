package com.ven.taskboard.notify.decorator;

import com.ven.taskboard.notify.bridge.NotificationChannel;

// Decorator — resiliency via simple retries.
public class RetryingNotificationChannel implements NotificationChannel {
    private final NotificationChannel delegate;
    private final int maxRetries;

    public RetryingNotificationChannel(NotificationChannel delegate, int maxRetries) {
        this.delegate = delegate;
        this.maxRetries = Math.max(0, maxRetries);
    }

    @Override
    public void send(String to, String subject, String body) {
        RuntimeException last = null;
        for (int i = 0; i <= maxRetries; i++) {
            try {
                delegate.send(to, subject, body);
                return;
            } catch (RuntimeException ex) {
                last = ex;
            }
        }
        throw last;
    }
}
