package com.ven.taskboard.persistence;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "boards")
public class BoardEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    // Optional policy string (e.g., for assignment strategies later)
    private String policy;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<ColumnEntity> columns = new ArrayList<>();

    protected BoardEntity() { }

    public BoardEntity(String name) {
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getPolicy() { return policy; }
    public List<ColumnEntity> getColumns() { return columns; }

    public void setPolicy(String policy) { this.policy = policy; }

    public ColumnEntity addColumn(String name, int wipLimit, int orderIndex) {
        ColumnEntity c = new ColumnEntity(this, name, wipLimit, orderIndex);
        this.columns.add(c);
        return c;
    }
}
