package com.ven.taskboard.notify.observer;

import com.ven.taskboard.notify.bridge.NotificationChannel;

/**
 * Observer, który wysyła powiadomienie przez kanał NotificationChannel (Adapter/Bridge).
 */
public class NotificationObserver implements Observer {

    private final NotificationChannel channel;
    private final String to;       // odbiorca powiadomienia
    private final String subject;  // temat wiadomości

    public NotificationObserver(NotificationChannel channel, String to, String subject) {
        this.channel = channel;
        this.to = to;
        this.subject = subject;
    }

    @Override
    public void update(String body) {
        // Wywołanie metody Adaptera/Bridge
        channel.send(to, subject, body);
    }
}
