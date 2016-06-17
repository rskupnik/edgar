package com.github.rskupnik.edgar.networking;

import com.github.rskupnik.edgar.executor.Executor;
import com.github.rskupnik.edgar.executor.tasks.InterpretCommandTask;
import com.github.rskupnik.edgar.networking.packets.CommandPacket;
import com.github.rskupnik.edgar.networking.packets.HandshakeResponsePacket;
import com.github.rskupnik.pigeon.commons.Connection;
import com.github.rskupnik.pigeon.commons.Packet;
import com.github.rskupnik.pigeon.commons.callback.ServerCallbackHandler;
import com.github.rskupnik.pigeon.commons.exceptions.PigeonException;
import com.github.rskupnik.pigeon.commons.glue.designpatterns.observer.Message;
import com.github.rskupnik.pigeon.tcpserver.Pigeon;
import com.github.rskupnik.pigeon.tcpserver.PigeonTcpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Network implements com.github.rskupnik.pigeon.commons.PacketHandler, ServerCallbackHandler {

    private static Network INSTANCE;

    public static Network getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Network();

        return INSTANCE;
    }

    private static final Logger log = LogManager.getLogger(Network.class);

    private PigeonTcpServer server;

    private Network() {

    }

    public void launch() throws PigeonException {
        server = Pigeon.newServer()
            .withPacketHandler(this)
            .withServerCallbackHandler(this)
            .build();

        server.start();
    }

    @Override
    public void handle(Packet packet) {
        try {
            switch (packet.getId()) {
                case 1:
                    log.debug("Received a packet of type: DISCONNECT_PACKET");
                    server.update(null, Message.DISCONNECTED, packet.getConnection().getUuid());
                    break;
                case 2:
                    CommandPacket pckt = (CommandPacket) packet;
                    log.debug("Received packet of type: COMMAND_PACKET");
                    log.debug("Command retrieved: " + pckt.getCommand());
                    Executor.getInstance().queue(new InterpretCommandTask(pckt.getConnection(), pckt.getCommand()));
                    break;
                case 3:
                    log.debug("Received packet of type: HANDSHAKE_PACKET");
                    server.send(new HandshakeResponsePacket(), packet.getConnection());
                    break;
                default:
                    break;
            }
        } catch (PigeonException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onNewConnection(Connection connection) {

    }

    @Override
    public void onDisconnected(Connection connection) {

    }

    public PigeonTcpServer getServer() {
        return server;
    }
}
