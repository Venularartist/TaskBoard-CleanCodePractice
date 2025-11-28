package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek.
 * Sprawdzają poprawne tworzenie iteratora oraz jego iterację po kolumnach.
 */
class IterableBoardTest {

    private BoardEntity board;
    private ColumnEntity col1;
    private ColumnEntity col2;
    private ColumnEntity col3;

    /**Piotrek.
     * Tworzy board i dodaje 3 kolumny przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        board = new BoardEntity("Test Board");

        col1 = board.addColumn("Column 1", 5, 0);
        col2 = board.addColumn("Column 2", 5, 1);
        col3 = board.addColumn("Column 3", 5, 2);
    }

    /**Piotrek.
     * Testuje, czy iterator zwracany przez IterableBoard działa poprawnie.
     */
    @Test
    void iteratorShouldReturnColumnsInOrder() {
        IterableBoard iterableBoard = new IterableBoard(board);
        Iterator<ColumnEntity> iterator = iterableBoard.createIterator();

        assertTrue(iterator.hasNext());
        assertEquals(col1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col3, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /**Piotrek.
     * Sprawdza, czy iterator rzuca wyjątek, gdy wywołamy next() po przejściu wszystkich elementów.
     */
    @Test
    void nextShouldThrowExceptionWhenNoMoreColumns() {
        IterableBoard iterableBoard = new IterableBoard(board);
        Iterator<ColumnEntity> iterator = iterableBoard.createIterator();

        iterator.next();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /** Piotrek.
     * Sprawdz a działanie iteratora na pustym boardzie.
     */
    @Test
    void iteratorOnEmptyBoardShouldBehaveCorrectly() {
        BoardEntity emptyBoard = new BoardEntity("Empty");
        IterableBoard iterableBoard = new IterableBoard(emptyBoard);
        Iterator<ColumnEntity> iterator = iterableBoard.createIterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
