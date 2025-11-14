package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

import java.util.List;
import java.util.NoSuchElementException;

public class BoardIterator implements Iterator<ColumnEntity> {

    protected final List<ColumnEntity> columns;
    protected int position = 0;

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
            throw new NoSuchElementException("No more columns in board");
        }
        return columns.get(position++);
    }
}
