package com.ven.taskboard.card.service;

import com.ven.taskboard.card.api.CardAssigner;
import com.ven.taskboard.card.api.CardCreator;
import com.ven.taskboard.card.api.CardMover;
import com.ven.taskboard.card.assign.AssignmentPolicy;
import com.ven.taskboard.card.assign.AssignmentStrategyFactory; // Factory
import com.ven.taskboard.card.service.command.AssignCardCommand;
import com.ven.taskboard.card.service.command.CommandExecutor;
import com.ven.taskboard.card.service.command.CreateCardCommand;
import com.ven.taskboard.card.service.command.MoveCardCommand;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.notify.bridge.CardAssignedNotification; // Bridge
import com.ven.taskboard.notify.bridge.Notification;            // Bridge: Abstraction
import com.ven.taskboard.notify.bridge.NotificationChannel;    // Bridge: Implementor
import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.AssignCardRequest;
import com.ven.taskboard.web.dto.CreateCardRequest;
import com.ven.taskboard.web.dto.MoveCardRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


// Command – kapsułuje akcję (AssignCardCommand, MoveCardCommand, CreateCardCommand)
// Template Method – definiuje algorytm w klasach operacji (AssignCardOperation itp.)
// CardService – wywołuje komendy przez CommandExecutor.
// DIP (#3): moduł wysokopoziomowy zależy od ABSTRAKCJI (Notification, NotificationChannel),
// a nie od modułów niskopoziomowych (konkretne kanały/adaptery).

@Service
public class CardService implements CardCreator, CardMover, CardAssigner { //"implementacja" posegmentowanych interfejsów

    private final CardRepository cards;
    private final ColumnRepository columns;
    private final AssignmentStrategyFactory strategies;
    private final NotificationChannel notificationChannel; // DIP: interface (Adapter/Bridge)
    private final CommandExecutor executor;

    public CardService(CardRepository cards,
                       ColumnRepository columns,
                       AssignmentStrategyFactory strategies,
                       NotificationChannel notificationChannel,
                       CommandExecutor executor) {
        this.cards = cards;
        this.columns = columns;
        this.strategies = strategies;
        this.notificationChannel = notificationChannel;
        this.executor = executor;
    }

    // Command + Template (Create) użycie
    @Transactional
    public UUID create(CreateCardRequest req) {
        return executor.execute(new CreateCardCommand(cards, columns, req));
    }

    // Command + Template (Move) użycie
    @Transactional
    public void move(UUID cardId, MoveCardRequest req) {
        executor.execute(new MoveCardCommand(cards, columns, cardId, req));
    }

    // Strategy + Bridge Odwracanie zależności (DIP) użycie
    @Transactional
    public void assign(UUID cardId, AssignCardRequest req) {
        // Pobranie karty
        CardEntity card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));

        // Strategy (przez Factory): wybór assignee
        String assignee = (req.assignee() != null && !req.assignee().isBlank())
                ? req.assignee()
                : strategies
                .get(req.policy() != null ? req.policy() : AssignmentPolicy.ROUND_ROBIN)
                .pickAssignee(card.getColumn().getBoard(), card);

        card.assignTo(assignee);
        cards.save(card);

        // DIP CardAssignedNotification może być wymieniony bez zmian w serwisie.
        Notification notification = new CardAssignedNotification(notificationChannel, card);
        notification.dispatch(assignee);
    }
}
