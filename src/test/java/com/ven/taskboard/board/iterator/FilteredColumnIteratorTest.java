package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.CardEntity;
import com.ven.taskboard.persistence.ColumnEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/** Piotrek
 * Sprawdzają iterację po kartach spełniających filtr oraz poprawne działanie przy pustej kolumnie lub braku dopasowań
 */
class FilteredColumnIteratorTest {

    private ColumnEntity column;
    private CardEntity card1;
    private CardEntity card2;
    private CardEntity card3;

    /** Piotrek
     * Tworzy kolumnę i dodaje 3 karty przed każdym testem
     */
    @BeforeEach
    void setUp() {
        column = new ColumnEntity(null, "Test Column", 5, 0);

        card1 = CardEntity.create(column, "Task 1", "Desc1", null, LocalDate.now(), "TODO", null);
        card2 = CardEntity.create(column, "Task 2", "Desc2", null, LocalDate.now(), "DONE", null);
        card3 = CardEntity.create(column, "Task 3", "Desc3", null, LocalDate.now(), "TODO", null);

        column.addCard(card1);
        column.addCard(card2);
        column.addCard(card3);
    }

    /**Piotrek
     * Iteracja powinna zwracać tylko karty spełniające filtr
     */
    @Test
    void iteratorShouldReturnOnlyMatchingCards() {
        Predicate<CardEntity> todoFilter = card -> "TODO".equals(card.getStatus());

        FilteredColumnIterator iterator = new FilteredColumnIterator(column, todoFilter);

        assertTrue(iterator.hasNext());
        assertEquals(card1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(card3, iterator.next());

        assertFalse(iterator.hasNext());
    }

    /** Piotrek
     * Sprawdza rzucenie wyjątku przy próbie pobrania kolejnego elementu gdy żadne karty nie pasują do filtra
     */
    @Test
    void nextShouldThrowExceptionWhenNoMatchingCards() {
        Predicate<CardEntity> noneFilter = card -> "NON_EXISTENT_STATUS".equals(card.getStatus());

        FilteredColumnIterator iterator = new FilteredColumnIterator(column, noneFilter);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /** Piotrek.
     * Sprawdza działanie iteratora na pustej kolumnie
     */
    @Test
    void iteratorOnEmptyColumnShouldBehaveCorrectly() {
        ColumnEntity emptyColumn = new ColumnEntity(null, "Empty", 5, 0);
        Predicate<CardEntity> filter = card -> true;

        FilteredColumnIterator iterator = new FilteredColumnIterator(emptyColumn, filter);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}

// to sie generuje automatycznie -lol
//package com.ven.taskboard.board.iterator;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FilteredColumnIteratorTest {
//
//    @Test
//    void hasNext() {
//    }
//
//    @Test
//    void next() {
//    }
//}