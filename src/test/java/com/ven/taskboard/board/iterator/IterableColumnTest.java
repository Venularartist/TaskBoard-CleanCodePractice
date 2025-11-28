package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek.
 * Sprawdzają poprawne tworzenie iteratora oraz jego iterację po kartach.
 */
class IterableColumnTest {

    private ColumnEntity column;
    private CardEntity card1;
    private CardEntity card2;
    private CardEntity card3;

    /** Piotrek.
     * Tworzy kolumnę i dodaje 3 karty przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        column = new ColumnEntity(null, "Test Column", 5, 0);

        card1 = CardEntity.create(column, "Card 1", "Desc1", null, LocalDate.now(), "TODO", null);
        card2 = CardEntity.create(column, "Card 2", "Desc2", null, LocalDate.now(), "DONE", null);
        card3 = CardEntity.create(column, "Card 3", "Desc3", null, LocalDate.now(), "TODO", null);

        column.addCard(card1);
        column.addCard(card2);
        column.addCard(card3);
    }

    /** Piotrek.
     * Testuje, czy iterator zwraca karty w kolejności dodania.
     */
    @Test
    void iteratorShouldReturnCardsInOrder() {
        IterableColumn iterableColumn = new IterableColumn(column);
        Iterator<CardEntity> iterator = iterableColumn.createIterator();

        assertTrue(iterator.hasNext());
        assertEquals(card1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(card2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(card3, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /** Piotrek.
     * Sprawdza, czy iterator rzuca wyjątek po przejściu wszystkich kart.
     */
    @Test
    void nextShouldThrowExceptionWhenNoMoreCards() {
        IterableColumn iterableColumn = new IterableColumn(column);
        Iterator<CardEntity> iterator = iterableColumn.createIterator();

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
        IterableColumn iterableColumn = new IterableColumn(emptyColumn);
        Iterator<CardEntity> iterator = iterableColumn.createIterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
