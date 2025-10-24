package com.ven.taskboard.board.iterator;

public interface Iterator<T> {
    boolean hasNext();
    T next();
}