package rskupnik.edgar.networking.packethandling.receive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;
import rskupnik.edgar.glue.designpatterns.observer.Message;
import rskupnik.edgar.networking.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

final class DisconnectPacketHandler extends PacketHandler {

    private static final Logger log = LogManager.getLogger(DisconnectPacketHandler.class);

    DisconnectPacketHandler() {

    }

    @Override
    int getHandledPacketId() {
        return PacketId.DISCONNECT_PACKET;
    }

    @Override
    void handlePacket(DataInputStream inputStream, UUID connectionId) throws IOException {
        log.debug("Received a packet of type: DISCONNECT_PACKET");
        Server.getInstance().update(null, Message.DISCONNECTED, connectionId);
    }

    @Override
    public Handler next() {
        return new CommandPacketHandler();
    }
}
