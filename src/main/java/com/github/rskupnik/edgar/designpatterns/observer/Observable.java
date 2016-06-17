package com.github.rskupnik.edgar.designpatterns.observer;

public interface Observable {
    void notify(com.github.rskupnik.edgar.designpatterns.observer.Message message, Object payload);
    void attach(Observer observer);
}
