package rskupnik.edgar.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.observer.Message;
import rskupnik.edgar.observer.Observable;
import rskupnik.edgar.observer.Observer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Thread implements Observable {

    private static final Logger logger = LogManager.getLogger(Connection.class);

    private final ServerSocket serverSocket;
    private final Socket clientSocket;
    private final List<Observer> observers = new ArrayList<Observer>();

    private boolean exit;

    protected Connection(ServerSocket serverSocket, Socket clientSocket) {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (!exit) {

        }
    }

    public void notify(Message message) {
        observers.forEach(observer -> observer.update(this, message));
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }
}
