package com.ven.taskboard.board.iterator;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.ColumnEntity;

public class ReversedIterableBoard extends IterableBoard {

    public ReversedIterableBoard(BoardEntity board) {
        super(board);
    }

    @Override
    public Iterator<ColumnEntity> createIterator() {
        return new ReversedBoardIterator(board);
    }
}

//Opis do 1 Liskov ~Piotrek

//Dodałem trzy nowe klasy pochodne, rozszerzające funkcjonalność istniejących iteratorów:
//
//        1. ReversedBoardIterator (podklasa BoardIterator)
//
//→ Iteruje kolumny od końca, ale zachowuje ten sam interfejs i kontrakt.
//
//        2. FilteredColumnIterator (podklasa ColumnIterator)
//
//→ Zwraca tylko karty spełniające filtr (Predicate<CardEntity>), nadal zachowując zasady Iteratora.
//
//        3. ReversedIterableBoard (podklasa IterableBoard)
//
//→ Tworzy reversed iterator, ale działa identycznie jak IterableBoard z punktu widzenia użytkownika.
//
//CO ZOSTAŁO ZMIENIONE
//
//Aby umożliwić poprawne dziedziczenie:
//
//        ✔ BoardIterator – pole columns i position zmieniono na protected, zamieniono wyjątek na NoSuchElementException.
//        ✔ ColumnIterator – to samo: cards i position na protected, poprawiony wyjątek.
//        ✔ IterableBoard – pole board zmienione na protected, aby podklasy miały dostęp.
//
//        1. Każda klasa pochodna zachowuje ten sam kontrakt co baza
//Przykład:
//
//ReversedBoardIterator nadal:
//
//ma hasNext()
//
//ma next()
//
//iteruje po kolumnach
//
//Zmienia się tylko kolejność, ale interfejs i gwarancje pozostają te same.
//
//        ✔ Klient oczekujący Iteratora nie musi nic zmieniać.
//
//Dodane trzy podklasy rozszerzają istniejące iteratory, nie zmieniając ich zachowania, kontraktu ani oczekiwań — dzięki czemu obiekty klas pochodnych mogą być używane w tych samych miejscach co obiekty klas bazowych, co dokładnie wypełnia zasadę podstawienia Liskov.
