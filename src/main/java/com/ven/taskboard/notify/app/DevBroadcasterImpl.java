package com.ven.taskboard.notify.app;

import com.ven.taskboard.notify.api.DevBroadcaster;
import com.ven.taskboard.notify.bridge.NotificationChannel;
import org.springframework.stereotype.Component;

@Component
public class DevBroadcasterImpl implements DevBroadcaster {
    private final NotificationChannel channel;
    public DevBroadcasterImpl(NotificationChannel channel) { this.channel = channel; }

    @Override
    public void send(String to, String subject, String body) {
        channel.send(to, subject, body);
    }
}
