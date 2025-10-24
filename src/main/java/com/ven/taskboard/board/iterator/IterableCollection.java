package com.ven.taskboard.board.iterator;

public interface IterableCollection<T> {
    Iterator<T> createIterator();
}