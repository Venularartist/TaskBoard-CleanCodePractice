package com.ven.taskboard.notify.api;

public interface DevBroadcaster {
    void send(String to, String subject, String body);
}
