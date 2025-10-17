package com.ven.taskboard.notify.bridge;

public interface NotificationChannel {
    void send(String to, String subject, String body);
}
