package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek.
 * Sprawdzają poprawne tworzenie iteratora w odwrotnej kolejności oraz jego działanie na pustym boardzie
 */
class ReversedIterableBoardTest {

    private BoardEntity board;
    private ColumnEntity col1;
    private ColumnEntity col2;
    private ColumnEntity col3;

    /** Piotrek.
     * Tworzy board i dodaje 3 kolumny przed każdym testm
     */
    @BeforeEach
    void setUp() {
        board = new BoardEntity("Test Board");

        col1 = board.addColumn("Column 1", 5, 0);
        col2 = board.addColumn("Column 2", 5, 1);
        col3 = board.addColumn("Column 3", 5, 2);
    }

    /** Piotrek.
     * Sprawdza, czy iterator zwraca kolumny w odwrotnej !!! kolejności
     */
    @Test
    void iteratorShouldReturnColumnsInReverseOrder() {
        ReversedIterableBoard iterableBoard = new ReversedIterableBoard(board);
        Iterator<ColumnEntity> iterator = iterableBoard.createIterator();

        assertTrue(iterator.hasNext());
        assertEquals(col3, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col1, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /** Piotrek.
     * Sprawdza rzucanie wyjątku gdy next() wywołamy po zakończeniu iterowania
     */
    @Test
    void nextShouldThrowExceptionWhenNoMoreColumns() {
        ReversedIterableBoard iterableBoard = new ReversedIterableBoard(board);
        Iterator<ColumnEntity> iterator = iterableBoard.createIterator();

        iterator.next();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /** Piotrek.
     * Sprawdza działanie iteratora na pustym board
     */
    @Test
    void iteratorOnEmptyBoardShouldBehaveCorrectly() {
        BoardEntity emptyBoard = new BoardEntity("Empty");
        ReversedIterableBoard iterableBoard = new ReversedIterableBoard(emptyBoard);
        Iterator<ColumnEntity> iterator = iterableBoard.createIterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
