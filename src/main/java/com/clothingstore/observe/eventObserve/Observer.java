package com.clothingstore.observe.eventObserve;


import com.clothingstore.model.Notification;

public interface Observer {
    void update(Notification notification);
}