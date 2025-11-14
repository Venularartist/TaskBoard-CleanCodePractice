package com.ven.taskboard.notify.app;

import com.ven.taskboard.notify.api.AssignmentNotifier;
import com.ven.taskboard.notify.bridge.CardAssignedNotification;
import com.ven.taskboard.notify.bridge.Notification;
import com.ven.taskboard.notify.bridge.NotificationChannel;
import com.ven.taskboard.persistence.CardEntity;
import org.springframework.stereotype.Component;

@Component
public class AssignmentNotifierImpl implements AssignmentNotifier {
    private final NotificationChannel channel;
    public AssignmentNotifierImpl(NotificationChannel channel) { this.channel = channel; }

    @Override
    public void notifyAssigned(CardEntity card, String assignee) {
        Notification n = new CardAssignedNotification(channel, card);
        n.dispatch(assignee);
    }
}
