package com.ven.taskboard.notify.bridge;

// Bridge — Abstraction (Notification) uses Implementor (NotificationChannel).
public abstract class Notification {
    protected final NotificationChannel channel;
    protected Notification(NotificationChannel channel) { this.channel = channel; }
    public abstract void dispatch(String recipient);
}
