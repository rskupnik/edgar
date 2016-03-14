package rskupnik.edgar.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.observer.Message;
import rskupnik.edgar.glue.designpatterns.observer.Observable;
import rskupnik.edgar.glue.designpatterns.observer.Observer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final class Connection extends Thread implements Observable {

    private static final Logger logger = LogManager.getLogger(Connection.class);

    private static final int COMMAND_PACKET = 0x01;

    private UUID uuid;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream clientInputStream;
    private DataOutputStream clientOutputStream;
    private List<Observer> observers = new ArrayList<Observer>();

    private boolean exit;
    private boolean ok = true;

    protected Connection(UUID uuid, ServerSocket serverSocket, Socket clientSocket) {
        this.setName("Connection-"+uuid.toString().substring(0, 8));
        try {
            this.uuid = uuid;
            this.serverSocket = serverSocket;
            this.clientSocket = clientSocket;
            this.clientInputStream = new DataInputStream(clientSocket.getInputStream());
            this.clientOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            ok = false;
        }
    }

    @Override
    public void run() {
        try {
            while (!exit) {
                int packetId = clientInputStream.read();

                if (packetId == -1) {
                    notify(Message.DISCONNECTED, uuid);
                    exit = true;
                    continue;
                }

                logger.debug("Received a packet, ID: "+packetId);
                handle(packetId);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            notify(Message.DISCONNECTED, uuid);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void handle(int packetId) {
        try {
            switch (packetId) {
                case COMMAND_PACKET:
                    logger.debug("Packet type: COMMAND_PACKET");
                    String cmd = clientInputStream.readUTF();
                    logger.debug("Command retrieved: "+cmd);
                    break;
                default:
                    logger.error("Unknown packet id: " + packetId);
                    break;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void notify(Message message, Object payload) {
        logger.debug("Sending a message of type "+message+" with payload: "+payload);
        observers.forEach(observer -> observer.update(this, message, payload));
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public boolean isOk() {
        return ok;
    }

    public String getHost() {
        return clientSocket.getInetAddress().getHostAddress();
    }
}
