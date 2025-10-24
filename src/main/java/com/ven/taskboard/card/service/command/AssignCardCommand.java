package com.ven.taskboard.card.service.command;

import com.ven.taskboard.card.assign.AssignmentStrategyFactory;
import com.ven.taskboard.card.service.template.AssignCardOperation;
import com.ven.taskboard.notify.bridge.NotificationChannel;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.web.dto.AssignCardRequest;

import java.util.UUID;

public class AssignCardCommand implements Command<Void> {

    private final CardRepository cards;
    private final UUID cardId;
    private final AssignCardRequest request;
    private final AssignmentStrategyFactory strategies;
    private final NotificationChannel notificationChannel;

    public AssignCardCommand(CardRepository cards,
                             UUID cardId,
                             AssignCardRequest request,
                             AssignmentStrategyFactory strategies,
                             NotificationChannel notificationChannel) {
        this.cards = cards;
        this.cardId = cardId;
        this.request = request;
        this.strategies = strategies;
        this.notificationChannel = notificationChannel;
    }

    @Override
    public Void execute() {
        return new AssignCardOperation(cards, cardId, request, strategies, notificationChannel).execute();
    }
}