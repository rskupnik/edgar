package rskupnik.edgar.observer;

public interface Observable {
    void notify(Message message);
    void attach(Observer observer);
}
