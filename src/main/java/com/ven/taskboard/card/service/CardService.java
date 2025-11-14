package com.ven.taskboard.card.service;

import com.ven.taskboard.card.assign.AssignmentPolicy;
import com.ven.taskboard.card.assign.AssignmentStrategyFactory; // Factory
import com.ven.taskboard.card.builder.CardBuilder;             // Builder
import com.ven.taskboard.card.service.command.AssignCardCommand;
import com.ven.taskboard.card.service.command.CommandExecutor;
import com.ven.taskboard.card.service.command.CreateCardCommand;
import com.ven.taskboard.card.service.command.MoveCardCommand;
import com.ven.taskboard.card.service.template.AssignCardOperation;
import com.ven.taskboard.card.service.template.CreateCardOperation;
import com.ven.taskboard.card.service.template.MoveCardOperation;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.notify.bridge.CardAssignedNotification; // Bridge (Notification abstraction)
import com.ven.taskboard.notify.bridge.NotificationChannel;      // Bridge Implementor (decorated)
import com.ven.taskboard.notify.observer.CardSubject;
import com.ven.taskboard.notify.observer.NotificationObserver;
import com.ven.taskboard.persistence.*;
import com.ven.taskboard.web.dto.AssignCardRequest;
import com.ven.taskboard.web.dto.CreateCardRequest;
import com.ven.taskboard.web.dto.MoveCardRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


//Command – kapsułuje akcję (AssignCardCommand, MoveCardCommand, CreateCardCommand)
//
//Template Method – definiuje szczegółowy algorytm (AssignCardOperation, itd.)
//
//CardService – wywołuje komendy przez CommandExecutor.

@Service
public class CardService {

    private final CardRepository cards;
    private final ColumnRepository columns;
    private final AssignmentStrategyFactory strategies;
    private final NotificationChannel notificationChannel;
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


    // Strategy + Observer + Bridge użycie

    @Transactional
    public void assign(UUID cardId, AssignCardRequest req) {
        // Pobranie karty
        CardEntity card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));


        // Strategy: wybór assignee
        String assignee = (req.assignee() != null && !req.assignee().isBlank())
                ? req.assignee()
                : strategies.get(req.policy() != null ? req.policy() : AssignmentPolicy.ROUND_ROBIN)
                .pickAssignee(card.getColumn().getBoard(), card);

        card.assignTo(assignee);
        cards.save(card);


        // Observer użycie

        CardSubject subject = new CardSubject(card.getId());

        // Tworzymy obserwatora dla kanału NotificationChannel (Adapter/Bridge)
        NotificationObserver observer = new NotificationObserver(
                notificationChannel,
                assignee,
                "Card Assigned"
        );
        subject.attach(observer);

        // Wywołanie powiadomienia
        subject.notifyObservers("Card " + card.getTitle() + " has been assigned to " + assignee);
    }
}


//    @Transactional
//    public void assign(UUID cardId, AssignCardRequest req) {
//        executor.execute(new AssignCardCommand(cards, cardId, req, strategies, notificationChannel));
//    }


//    @Transactional
//    public UUID create(CreateCardRequest req) {
//        return executor.execute(new CreateCardCommand(req, cards, columns)); //Command użycie
//    }
//
//    @Transactional
//    public void move(UUID cardId, MoveCardRequest req) {
//        executor.execute(new MoveCardCommand(cardId, req, cards, columns)); //Command użycie
//    }
//
//    @Transactional
//    public void assign(UUID cardId, AssignCardRequest req) {
//        executor.execute(new AssignCardCommand(cardId, req, cards, strategies, notificationChannel)); //Command użycie
//    }


//@Service
//public class CardService {
//
//    private final CardRepository cards;
//    private final ColumnRepository columns;
//    private final AssignmentStrategyFactory strategies;     // Factory selection point
//    private final NotificationChannel notificationChannel;  // Injected channel (may yet be decorated)
//
//    public CardService(CardRepository cards,
//                       ColumnRepository columns,
//                       AssignmentStrategyFactory strategies,
//                       NotificationChannel notificationChannel) {
//        this.cards = cards;
//        this.columns = columns;
//        this.strategies = strategies;
//        this.notificationChannel = notificationChannel;
//    }
//
//    @Transactional
//    public UUID create(CreateCardRequest req) {
//        ColumnEntity column = columns.findById(req.columnId())
//                .orElseThrow(() -> new NotFoundException("Column not found: " + req.columnId()));
//
//        var card = new CardBuilder() // Builder użycie
//                .in(column)
//                .title(req.title())
//                .description(req.description())
//                .assignee(req.assignee())
//                .dueDate(req.dueDate())
//                .labels(req.labels())
//                .build();
//
//        // Owning side of @ManyToOne persists relation
//        cards.save(card);
//        return card.getId();
//    }
//
//    @Transactional
//    public void move(UUID cardId, MoveCardRequest req) {
//        var card = cards.findById(cardId)
//                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));
//
//        UUID destId = req.toColumnId();
//        var dest = columns.findById(destId)
//                .orElseThrow(() -> new NotFoundException("Destination column not found: " + destId));
//
//        card.moveTo(dest);
//        cards.save(card);
//    }
//
//    @Transactional
//    public void assign(UUID cardId, AssignCardRequest req) {
//        var card = cards.findById(cardId)
//                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));
//
//        // If explicit assignee provided, use it; otherwise select via policy
//        String assignee;
//        if (req.assignee() != null && !req.assignee().isBlank()) {
//            assignee = req.assignee();
//        } else {
//            AssignmentPolicy policy = (req.policy() != null) ? req.policy() : AssignmentPolicy.ROUND_ROBIN;
//            var board = card.getColumn().getBoard();
//            assignee = strategies.get(policy).pickAssignee(board, card); // Użycie Factory
//        }
//
//        card.assignTo(assignee);
//        cards.save(card);
//
//        // Bridge użycie (+ Decorator if configured) — send notification via channel
//        new CardAssignedNotification(notificationChannel, card).dispatch(assignee);
//    }
//}
