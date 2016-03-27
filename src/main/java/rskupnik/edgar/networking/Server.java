package rskupnik.edgar.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.observer.Message;
import rskupnik.edgar.glue.designpatterns.observer.Observable;
import rskupnik.edgar.glue.designpatterns.observer.Observer;
import rskupnik.edgar.networking.packethandling.packets.Packet;
import rskupnik.edgar.other.Constants;
import rskupnik.parrot.Parrot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Server extends Thread implements Observer {

    private static Server INSTANCE;
    private static final int PORT = Integer.parseInt(Parrot.get("port").orElse(String.valueOf(Constants.DEFAULT_PORT)));
    private static final Logger log = LogManager.getLogger(Server.class);

    private ServerSocket serverSocket;
    private final Map<UUID, Connection> connections = new HashMap<UUID, Connection>();

    private boolean exit;

    private Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
    }

    public static Server getInstance() {
        if (INSTANCE == null)
            launch();

        return INSTANCE;
    }

    public static void launch() {
        if (INSTANCE == null) {
            INSTANCE = new Server();
            INSTANCE.setDaemon(false);
            INSTANCE.setName("Edgar-server");
            INSTANCE.start();
        }
    }

    @Override
    public void run() {
        log.info("Listening on port "+PORT);
        try {
            while (!exit) {
                Socket clientSocket = serverSocket.accept();

                UUID uuid = UUID.randomUUID();
                Connection connection = new Connection(uuid, serverSocket, clientSocket);
                if (connection.isOk()) {    // Connection is not considered ok when there is an IOException in the constructor
                    connection.attach(this);
                    connections.put(uuid, connection);
                    log.info("Accepted a new connection from " + clientSocket.getInetAddress().getHostAddress());
                    log.trace("Assigned uuid: " + uuid);
                    connection.start();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void update(Observable observable, Message message, Object payload) {
        switch (message) {
            case DISCONNECTED:
                UUID connectionUuid = (UUID) payload;
                Connection connection = connections.get(connectionUuid);
                connections.remove(connectionUuid);
                log.debug("Removed connection ["+connectionUuid+"] from ["+connection.getHost()+"]");
                log.debug("Remaining connections: "+connections.entrySet().size());
                break;
        }
    }

    public void send(UUID connectionId, Packet packet) {
        Connection connection = connections.get(connectionId);

        if (connection == null) {
            log.debug("Connection ["+connectionId+"] doesn't exist, cannot send packet");
            return;
        }

        connection.send(packet);
    }
}
