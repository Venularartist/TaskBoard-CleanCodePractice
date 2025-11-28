package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek.
 * Testy jednostkowe dla BoardIterator.
 * Sprawdzają iterowanie po kolumnach, obsługę końca listy
 * oraz zachowanie przy pustej tablicy.
 */
class BoardIteratorTest {

    private BoardEntity board;
    private ColumnEntity col1;
    private ColumnEntity col2;
    private ColumnEntity col3;

    /** Piotrek.
     * Uruchamia się przed każdym testem booo @BeforeEach jest -  Tworzy nową tablicę i dodaje do niej 3 kolumny.
     */
    @BeforeEach
    void setUp() {
        board = new BoardEntity("Test Board");

        col1 = board.addColumn("Column 1", 5, 0);
        col2 = board.addColumn("Column 2", 5, 1);
        col3 = board.addColumn("Column 3", 5, 2);
    }

    /**Piotrek.
     * Sprawdza poprawne przechodzenie po kolumnach kolejno: col1, col2, col3.
     * Oraz poprawne działanie hasNext().
     */
    @Test
    void iteratorShouldReturnColumnsInOrder() {
        BoardIterator iterator = new BoardIterator(board);

        assertTrue(iterator.hasNext());
        assertEquals(col1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(col3, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /** Piotrek.
     * Sprawdza, czy wywołanie next() po przejściu wszystkich elementow rzuca NoSuchElementException.
     */
    @Test
    void callingNextWhenNoMoreElementsShouldThrowException() {
        BoardIterator iterator = new BoardIterator(board);

        iterator.next();
        iterator.next();
        iterator.next();

        // Po trzech next() lista się kończy a kolejny next() ma rzucić wyjątek
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /** Piotrek.
     * Sprawdza działanie iteratora na pustej tablicy kolumn.
     * hasNext() powinno zwrócić false,
     * a next() powinno rzucić wyjątek.
     */
    @Test
    void iteratorOnEmptyBoardShouldBehaveCorrectly() {
        BoardEntity emptyBoard = new BoardEntity("Empty");

        BoardIterator iterator = new BoardIterator(emptyBoard);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
