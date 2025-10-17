package com.ven.taskboard.card.builder;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

import java.time.LocalDate;
import java.util.List;

// Builder — step-by-step construction of CardEntity with validation.

public class CardBuilder {
    private ColumnEntity column;
    private String title;
    private String description;
    private String assignee;
    private LocalDate dueDate;
    private String status = "TODO";
    private List<String> labels = List.of();

    public CardBuilder in(ColumnEntity column) { this.column = column; return this; }
    public CardBuilder title(String title) { this.title = title; return this; }
    public CardBuilder description(String description) { this.description = description; return this; }
    public CardBuilder assignee(String assignee) { this.assignee = assignee; return this; }
    public CardBuilder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
    public CardBuilder status(String status) { this.status = status; return this; }
    public CardBuilder labels(List<String> labels) { this.labels = labels != null ? labels : List.of(); return this; }

    // Builder — finalize construction; enforce required fields (fail fast).

    public CardEntity build() {
        if (column == null) throw new IllegalStateException("column is required");
        if (title == null || title.isBlank()) throw new IllegalStateException("title is required");
        String csv = String.join(",", labels);
        return CardEntity.create(column, title, description, assignee, dueDate, status, csv);
    }
}
