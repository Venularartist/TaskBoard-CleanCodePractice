package com.ven.taskboard.card.service.template;

import com.ven.taskboard.common.NotFoundException;
//import com.ven.taskboard.persistence.CardBuilder;
import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.CardRepository;
import com.ven.taskboard.persistence.ColumnEntity;
import com.ven.taskboard.persistence.ColumnRepository;
import com.ven.taskboard.web.dto.CreateCardRequest;

import java.util.UUID;

public class CreateCardOperation extends CardOperationTemplate<UUID> { //Template Method użycie

    private final CardRepository cards;
    private final ColumnRepository columns;
    private final CreateCardRequest req;

    public CreateCardOperation(CardRepository cards, ColumnRepository columns, CreateCardRequest req) {
        this.cards = cards;
        this.columns = columns;
        this.req = req;
    }

    @Override
    protected void validate() {
        if (req.title() == null || req.title().isBlank()) {
            throw new IllegalArgumentException("Card title cannot be empty");
        }
    }

    @Override
    protected UUID doOperation() {
        ColumnEntity column = columns.findById(req.columnId())
                .orElseThrow(() -> new NotFoundException("Column not found: " + req.columnId()));

        CardEntity card = new com.ven.taskboard.card.builder.CardBuilder()
                .in(column)
                .title(req.title())
                .description(req.description())
                .assignee(req.assignee())
                .dueDate(req.dueDate())
                .labels(req.labels())
                .build();

        cards.save(card);
        return card.getId();
    }
}