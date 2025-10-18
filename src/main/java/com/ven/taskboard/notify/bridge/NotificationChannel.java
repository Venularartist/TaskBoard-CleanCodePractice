package com.ven.taskboard.notify.bridge;

public interface NotificationChannel {  //Adapter użycie
    void send(String to, String subject, String body);
}
