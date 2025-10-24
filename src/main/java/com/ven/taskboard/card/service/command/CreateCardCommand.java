package com.ven.taskboard.card.service.command;

import com.ven.taskboard.card.builder.CardBuilder;
import com.ven.taskboard.common.NotFoundException;
import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnEntity;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.CreateCardRequest;

import java.util.UUID;

public class CreateCardCommand implements Command<UUID> {

    private final CreateCardRequest request;
    private final CardRepository cards;
    private final ColumnRepository columns;

    public CreateCardCommand(CreateCardRequest request, CardRepository cards, ColumnRepository columns) {
        this.request = request;
        this.cards = cards;
        this.columns = columns;
    }

    @Override
    public UUID execute() {
        ColumnEntity column = columns.findById(request.columnId())
                .orElseThrow(() -> new NotFoundException("Column not found: " + request.columnId()));

        CardEntity card = new CardBuilder()
                .in(column)
                .title(request.title())
                .description(request.description())
                .assignee(request.assignee())
                .dueDate(request.dueDate())
                .labels(request.labels())
                .build();

        cards.save(card);
        return card.getId();
    }
}