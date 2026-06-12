package com.clothingstore.observe.eventObserve;

import com.clothingstore.model.Event;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Event event);
}