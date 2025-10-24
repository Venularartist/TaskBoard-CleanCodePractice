package com.ven.taskboard.card.service.command;

import com.ven.taskboard.card.service.template.CreateCardOperation;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.CreateCardRequest;

import java.util.UUID;

public class CreateCardCommand implements Command<UUID> {

    private final CardRepository cards;
    private final ColumnRepository columns;
    private final CreateCardRequest request;

    public CreateCardCommand(CardRepository cards, ColumnRepository columns, CreateCardRequest request) {
        this.cards = cards;
        this.columns = columns;
        this.request = request;
    }

    @Override
    public UUID execute() {
        return new CreateCardOperation(cards, columns, request).execute();
    }
}