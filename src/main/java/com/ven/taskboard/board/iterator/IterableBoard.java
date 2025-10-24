package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

public class IterableBoard implements IterableCollection<ColumnEntity> {

    private final BoardEntity board;

    public IterableBoard(BoardEntity board) {
        this.board = board;
    }

    @Override
    public Iterator<ColumnEntity> createIterator() {
        return new BoardIterator(board);
    }
}