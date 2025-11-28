package com.ven.taskboard.persistence;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "columns")
public class ColumnEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BoardEntity board;

    @Column(nullable = false)
    private String name;

    private int wipLimit = 0; // 0 = unlimited
    private int orderIndex = 0;

    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<CardEntity> cards = new ArrayList<>();

    public ColumnEntity() { }

    public ColumnEntity(BoardEntity board, String name, int wipLimit, int orderIndex) {
        this.board = board;
        this.name = name;
        this.wipLimit = wipLimit;
        this.orderIndex = orderIndex;
    }

    public UUID getId() { return id; }
    public BoardEntity getBoard() { return board; }
    public String getName() { return name; }
    public int getWipLimit() { return wipLimit; }
    public int getOrderIndex() { return orderIndex; }
    public List<CardEntity> getCards() { return cards; }

    public void addCard(CardEntity card) {
        cards.add(card);
    }
}
