package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredColumnIterator extends ColumnIterator {

    private final Predicate<CardEntity> filter;
    private CardEntity nextMatch;

    public FilteredColumnIterator(ColumnEntity column, Predicate<CardEntity> filter) {
        super(column);
        this.filter = filter;
        findNext();
    }

    private void findNext() {
        nextMatch = null;

        while (super.hasNext()) {
            CardEntity card = super.next();
            if (filter.test(card)) {
                nextMatch = card;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextMatch != null;
    }

    @Override
    public CardEntity next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        CardEntity result = nextMatch;
        findNext();
        return result;
    }
}
