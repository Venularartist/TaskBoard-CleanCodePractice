package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

import java.util.List;

public class BoardIterator implements Iterator<ColumnEntity> {

    private final List<ColumnEntity> columns;
    private int position = 0;

    public BoardIterator(BoardEntity board) {
        this.columns = board.getColumns();
    }

    @Override
    public boolean hasNext() {
        return position < columns.size();
    }

    @Override
    public ColumnEntity next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more columns in board");
        }
        return columns.get(position++);
    }
}