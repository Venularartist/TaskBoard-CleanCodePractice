package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek.
 * Sprawdzają iterację po kolumnach w odwrotnej kolejności oraz poprawne zachowanie przy pustym board
 */
class ReversedBoardIteratorTest {

    private BoardEntity board;
    private ColumnEntity col1;
    private ColumnEntity col2;
    private ColumnEntity col3;

    /** Piotrek.
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
     * Sprawdza, czy iterator zwraca kolumny w odwrotnej kolejności.
     */
    @Test
    void iteratorShouldReturnColumnsInReverseOrder() {
        ReversedBoardIterator iterator = new ReversedBoardIterator(board);

        assertTrue(iterator.hasNext());
        assertEquals(col3, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col1, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /**Piotrek.
     * Sprawdza rzucanie wyjątku gdy next() wywołamy po zakończeniu iteracji
     */
    @Test
    void nextShouldThrowExceptionWhenNoMoreColumns() {
        ReversedBoardIterator iterator = new ReversedBoardIterator(board);

        iterator.next();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /** Piotrek.
     * Sprawdza działanie iteratora na pustym boardzie.
     */
    @Test
    void iteratorOnEmptyBoardShouldBehaveCorrectly() {
        BoardEntity emptyBoard = new BoardEntity("Empty");
        ReversedBoardIterator iterator = new ReversedBoardIterator(emptyBoard);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
