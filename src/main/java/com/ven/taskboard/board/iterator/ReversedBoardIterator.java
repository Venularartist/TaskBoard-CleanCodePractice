package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

import java.util.NoSuchElementException;

public class ReversedBoardIterator extends BoardIterator {

    public ReversedBoardIterator(BoardEntity board) {
        super(board);
        this.position = columns.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return position >= 0;
    }

    @Override
    public ColumnEntity next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more columns in reversed board");
        }
        return columns.get(position--);
    }
}
