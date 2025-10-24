package com.ven.taskboard.card.service.command;

import com.ven.taskboard.card.assign.AssignmentPolicy;
import com.ven.taskboard.card.assign.AssignmentStrategyFactory;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.notify.bridge.CardAssignedNotification;
import com.ven.taskboard.notify.bridge.NotificationChannel;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.web.dto.AssignCardRequest;

import java.util.UUID;

public class AssignCardCommand implements Command<Void> {

    private final UUID cardId;
    private final AssignCardRequest request;
    private final CardRepository cards;
    private final AssignmentStrategyFactory strategies;
    private final NotificationChannel notificationChannel;

    public AssignCardCommand(UUID cardId,
                             AssignCardRequest request,
                             CardRepository cards,
                             AssignmentStrategyFactory strategies,
                             NotificationChannel notificationChannel) {
        this.cardId = cardId;
        this.request = request;
        this.cards = cards;
        this.strategies = strategies;
        this.notificationChannel = notificationChannel;
    }

    @Override
    public Void execute() {
        var card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));

        String assignee;
        if (request.assignee() != null && !request.assignee().isBlank()) {
            assignee = request.assignee();
        } else {
            AssignmentPolicy policy = (request.policy() != null) ? request.policy() : AssignmentPolicy.ROUND_ROBIN;
            var board = card.getColumn().getBoard();
            assignee = strategies.get(policy).pickAssignee(board, card);
        }

        card.assignTo(assignee);
        cards.save(card);

        new CardAssignedNotification(notificationChannel, card).dispatch(assignee);
        return null;
    }
}