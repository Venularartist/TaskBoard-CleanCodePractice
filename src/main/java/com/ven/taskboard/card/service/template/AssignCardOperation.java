package com.ven.taskboard.card.service.template;

import com.ven.taskboard.card.assign.AssignmentPolicy;
import com.ven.taskboard.card.assign.AssignmentStrategyFactory;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.notify.bridge.CardAssignedNotification;
import com.ven.taskboard.notify.bridge.NotificationChannel;
import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.web.dto.AssignCardRequest;

import java.util.UUID;

public class AssignCardOperation extends CardOperationTemplate<Void> {

    private final CardRepository cards;
    private final UUID cardId;
    private final AssignCardRequest req;
    private final AssignmentStrategyFactory strategies;
    private final NotificationChannel notificationChannel;

    public AssignCardOperation(CardRepository cards,
                               UUID cardId,
                               AssignCardRequest req,
                               AssignmentStrategyFactory strategies,
                               NotificationChannel notificationChannel) {
        this.cards = cards;
        this.cardId = cardId;
        this.req = req;
        this.strategies = strategies;
        this.notificationChannel = notificationChannel;
    }

    @Override
    protected Void doOperation() {
        CardEntity card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));

        String assignee;
        if (req.assignee() != null && !req.assignee().isBlank()) {
            assignee = req.assignee();
        } else {
            var policy = req.policy() != null ? req.policy() : AssignmentPolicy.ROUND_ROBIN;
            var board = card.getColumn().getBoard();
            assignee = strategies.get(policy).pickAssignee(board, card);
        }

        card.assignTo(assignee);
        cards.save(card);

        new CardAssignedNotification(notificationChannel, card).dispatch(assignee);
        return null;
    }
}