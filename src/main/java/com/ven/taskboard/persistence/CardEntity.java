package com.ven.taskboard.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ColumnEntity column;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    private String assignee;            // e.g., "user:karol"
    private LocalDate dueDate;
    private String status = "TODO";
    private String labelsCsv = "";      // simple for now

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    protected CardEntity() { }

    // Package-private constructor used by the builder/factory
    CardEntity(ColumnEntity column, String title, String description, String assignee,
               LocalDate dueDate, String status, String labelsCsv) {
        this.column = column;
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.dueDate = dueDate;
        if (status != null) this.status = status;
        if (labelsCsv != null) this.labelsCsv = labelsCsv;
    }

    // Builder support — named factory used by CardBuilder to create a valid CardEntity.

    public static CardEntity create(ColumnEntity column, String title, String description, String assignee,
                                    LocalDate dueDate, String status, String labelsCsv) {
        return new CardEntity(column, title, description, assignee, dueDate, status, labelsCsv);
    }

    public UUID getId() { return id; }
    public ColumnEntity getColumn() { return column; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAssignee() { return assignee; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public String getLabelsCsv() { return labelsCsv; }
    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void moveTo(ColumnEntity newColumn) {
        this.column = newColumn;
    }

    // CC: Intention-revealing — domain change via a named method.
    public void assignTo(String assignee) {
        this.assignee = assignee;
    }
}
