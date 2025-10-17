package com.ven.taskboard.notify.bridge;

import com.ven.taskboard.persistence.CardEntity;

// Specific message type; channel is injected (Bridge).
public class CardAssignedNotification extends Notification {
    private final CardEntity card;
    public CardAssignedNotification(NotificationChannel channel, CardEntity card) {
        super(channel);
        this.card = card;
    }
    @Override
    public void dispatch(String recipient) {
        String subject = "Card assigned: " + card.getTitle();
        String body = "You have been assigned to: " + card.getTitle();
        channel.send(recipient, subject, body);
    }
}
