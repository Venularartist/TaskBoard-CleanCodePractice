package com.ven.taskboard.notify.bridge;

import com.ven.taskboard.persistence.CardEntity;

// Bridge

// dispatch(String recipient) crafts the message (subject/body) from the CardEntity and
// delegates to the injected NotificationChannel

// It handles what message to send for a “card assigned” event
// Passes the delivery part to the NotificationChannel

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
