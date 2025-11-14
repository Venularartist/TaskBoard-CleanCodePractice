package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

import java.util.List;
import java.util.NoSuchElementException;

public class ColumnIterator implements Iterator<CardEntity> {

    protected final List<CardEntity> cards;
    protected int position = 0;

    public ColumnIterator(ColumnEntity column) {
        this.cards = column.getCards();
    }

    @Override
    public boolean hasNext() {
        return position < cards.size();
    }

    @Override
    public CardEntity next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more cards in column");
        }
        return cards.get(position++);
    }
}
