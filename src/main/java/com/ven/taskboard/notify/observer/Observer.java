package com.ven.taskboard.notify.observer;

public interface Observer {
    void update(String message);
}
//Observer is a behavioral design pattern that lets you define
// a subscription mechanism to notify multiple objects about any events that happen to the object they’re observing.