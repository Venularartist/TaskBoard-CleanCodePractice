package com.ven.taskboard.notify.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardSubject implements Subject {

    private final UUID cardId;
    private final List<Observer> observers = new ArrayList<>();

    public CardSubject(UUID cardId) {
        this.cardId = cardId;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public UUID getCardId() {
        return cardId;
    }
}
