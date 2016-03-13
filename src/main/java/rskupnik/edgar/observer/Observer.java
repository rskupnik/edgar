package rskupnik.edgar.observer;

public interface Observer {
    void update(Observable observable, Message message, Object payload);
}
