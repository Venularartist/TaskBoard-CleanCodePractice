package com.ven.taskboard.card.service;

import com.ven.taskboard.card.builder.CardBuilder;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.persistence.*;
import com.ven.taskboard.web.dto.CreateCardRequest;
import com.ven.taskboard.web.dto.MoveCardRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cards;
    private final ColumnRepository columns;

    public CardService(CardRepository cards, ColumnRepository columns) {
        this.cards = cards;
        this.columns = columns;
    }

    @Transactional
    public UUID create(CreateCardRequest req) {
        ColumnEntity column = columns.findById(req.columnId())
                .orElseThrow(() -> new NotFoundException("Column not found: " + req.columnId()));

        CardEntity card = new CardBuilder()
                .in(column)
                .title(req.title())
                .description(req.description())
                .assignee(req.assignee())
                .dueDate(req.dueDate())
                .labels(req.labels())
                .build();

//        column.addCard(card);
        cards.save(card);
        return card.getId();
    }

    @Transactional
    public void move(UUID cardId, MoveCardRequest req) {
        CardEntity card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));
        ColumnEntity dest = columns.findById(req.toColumnId())
                .orElseThrow(() -> new NotFoundException("Destination column not found: " + req.toColumnId()));
        card.moveTo(dest);
        cards.save(card);
    }
}
