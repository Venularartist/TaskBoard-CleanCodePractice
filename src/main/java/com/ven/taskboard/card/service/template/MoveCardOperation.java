package com.ven.taskboard.card.service.template;

import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnEntity;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.MoveCardRequest;

import java.util.UUID;

public class MoveCardOperation extends CardOperationTemplate<Void> { //Template Method użycie

    private final CardRepository cards;
    private final ColumnRepository columns;
    private final UUID cardId;
    private final MoveCardRequest req;

    public MoveCardOperation(CardRepository cards, ColumnRepository columns, UUID cardId, MoveCardRequest req) {
        this.cards = cards;
        this.columns = columns;
        this.cardId = cardId;
        this.req = req;
    }

    @Override
    protected Void doOperation() {
        CardEntity card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));

        ColumnEntity dest = columns.findById(req.toColumnId())
                .orElseThrow(() -> new NotFoundException("Destination column not found: " + req.toColumnId()));

        card.moveTo(dest);
        cards.save(card);
        return null;
    }
}