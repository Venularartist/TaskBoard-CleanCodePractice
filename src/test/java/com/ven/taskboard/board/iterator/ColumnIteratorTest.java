package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek
 * Sprawdzają iterowanie po kartach, koniec listy oraz zachowanie przy pustej kolumnie.
 */
class ColumnIteratorTest {

    private ColumnEntity column;
    private CardEntity card1;
    private CardEntity card2;
    private CardEntity card3;

    /** Piorek.
     * Tworzy nową kolumnę i dodaje 3 karty.
     */
    @BeforeEach
    void setUp() {
        column = new ColumnEntity(null, "Test Column", 5, 0);

        card1 = CardEntity.create(column, "Card 1", "Desc1", null, LocalDate.now(), null, null);
        card2 = CardEntity.create(column, "Card 2", "Desc2", null, LocalDate.now(), null, null);
        card3 = CardEntity.create(column, "Card 3", "Desc3", null, LocalDate.now(), null, null);

        column.addCard(card1);
        column.addCard(card2);
        column.addCard(card3);
    }

    /** Piotrek.
     * Sprawdza, czy iterator zwraca karty w odpowiedniej kolejności.
     */
    @Test
    void iteratorShouldReturnCardsInOrder() {
        ColumnIterator iterator = new ColumnIterator(column);

        assertTrue(iterator.hasNext());
        assertEquals(card1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(card2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(card3, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /** Piotrek.
     * Sprawdza poprawne rzucenie wyjątku, gdy next() zostanie wywołany po przejściu wszystkich kart.
     */
    @Test
    void callingNextWhenNoMoreElementsShouldThrowException() {
        ColumnIterator iterator = new ColumnIterator(column);

        iterator.next();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /** Piotrek.
     * Sprawdza działanie iteratora na pustej kolumnie.
     */
    @Test
    void iteratorOnEmptyColumnShouldBehaveCorrectly() {
        ColumnEntity emptyColumn = new ColumnEntity(null, "Empty", 5, 0);

        ColumnIterator iterator = new ColumnIterator(emptyColumn);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
