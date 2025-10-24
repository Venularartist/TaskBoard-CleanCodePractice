package com.ven.taskboard.card.service.command;

import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.MoveCardRequest;

import java.util.UUID;

public class MoveCardCommand implements Command<Void> {

    private final UUID cardId;
    private final MoveCardRequest request;
    private final CardRepository cards;
    private final ColumnRepository columns;

    public MoveCardCommand(UUID cardId, MoveCardRequest request, CardRepository cards, ColumnRepository columns) {
        this.cardId = cardId;
        this.request = request;
        this.cards = cards;
        this.columns = columns;
    }

    @Override
    public Void execute() {
        var card = cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found: " + cardId));

        var dest = columns.findById(request.toColumnId())
                .orElseThrow(() -> new NotFoundException("Destination column not found: " + request.toColumnId()));

        card.moveTo(dest);
        cards.save(card);
        return null;
    }
}