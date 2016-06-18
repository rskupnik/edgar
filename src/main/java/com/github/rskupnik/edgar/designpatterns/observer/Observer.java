package com.github.rskupnik.edgar.designpatterns.observer;

public interface Observer {
    void update(Observable observable, com.github.rskupnik.edgar.designpatterns.observer.Message message, Object payload);
}
