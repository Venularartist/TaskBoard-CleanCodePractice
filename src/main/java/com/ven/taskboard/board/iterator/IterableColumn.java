package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

public class IterableColumn implements IterableCollection<CardEntity> {

    private final ColumnEntity column;

    public IterableColumn(ColumnEntity column) {
        this.column = column;
    }

    @Override
    public Iterator<CardEntity> createIterator() {
        return new ColumnIterator(column);
    }
}