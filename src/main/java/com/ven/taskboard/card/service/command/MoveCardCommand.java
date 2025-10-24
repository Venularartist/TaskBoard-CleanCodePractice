package com.ven.taskboard.card.service.command;

import com.ven.taskboard.card.service.template.MoveCardOperation;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.MoveCardRequest;

import java.util.UUID;

public class MoveCardCommand implements Command<Void> {

    private final CardRepository cards;
    private final ColumnRepository columns;
    private final UUID cardId;
    private final MoveCardRequest request;

    public MoveCardCommand(CardRepository cards, ColumnRepository columns, UUID cardId, MoveCardRequest request) {
        this.cards = cards;
        this.columns = columns;
        this.cardId = cardId;
        this.request = request;
    }

    @Override
    public Void execute() {
        return new MoveCardOperation(cards, columns, cardId, request).execute();
    }
}