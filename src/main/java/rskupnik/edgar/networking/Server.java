package rskupnik.edgar.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.observer.Message;
import rskupnik.edgar.observer.Observable;
import rskupnik.edgar.observer.Observer;
import rskupnik.edgar.other.Constants;
import rskupnik.parrot.Parrot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Server extends Thread implements Observer {

    private static Server INSTANCE;
    private static final int PORT = Integer.parseInt(Parrot.get("port").orElse(String.valueOf(Constants.DEFAULT_PORT)));
    private static final Logger logger = LogManager.getLogger(Server.class);

    private ServerSocket serverSocket;
    private final Map<UUID, Connection> connections = new HashMap<UUID, Connection>();

    private boolean exit;

    private Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            logger.info("Listening on port "+PORT);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(0);
        }
    }

    public static Server getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Server();

        return INSTANCE;
    }

    public static void launch() {
        if (INSTANCE == null)
            INSTANCE = new Server();
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(serverSocket, clientSocket);
                connection.attach(this);
                UUID uuid = UUID.randomUUID();
                connections.put(uuid, connection);
                logger.info("Accepted a new connection from "+clientSocket.getInetAddress().getHostAddress());
                logger.debug("Assigned uuid: "+uuid);
                connection.start();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void update(Observable observable, Message message) {
        switch (message) {
            case DISCONNECTED:
                break;
        }
    }
}
