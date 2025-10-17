package com.ven.taskboard.notify.decorator;

import com.ven.taskboard.notify.bridge.NotificationChannel;

// Decorator — adds logging without modifying the underlying channel.
public class LoggingNotificationChannel implements NotificationChannel {
    private final NotificationChannel delegate;
    public LoggingNotificationChannel(NotificationChannel delegate) { this.delegate = delegate; }

    @Override
    public void send(String to, String subject, String body) {
        long t0 = System.nanoTime();
        try {
            delegate.send(to, subject, body);
        } finally {
            long ms = (System.nanoTime() - t0) / 1_000_000;
            System.out.println("[NOTIFY][log] to=" + to + " took=" + ms + "ms");
        }
    }
}
