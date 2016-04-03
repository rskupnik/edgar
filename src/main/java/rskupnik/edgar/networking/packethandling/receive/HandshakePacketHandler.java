package rskupnik.edgar.networking.packethandling.receive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;
import rskupnik.edgar.networking.Server;
import rskupnik.edgar.networking.packethandling.send.HandshakeResponsePacket;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

final class HandshakePacketHandler extends PacketHandler {

    private static final Logger log = LogManager.getLogger(HandshakePacketHandler.class);

    HandshakePacketHandler() {

    }

    @Override
    int getHandledPacketId() {
        return PacketId.HANDSHAKE_PACKET;
    }

    @Override
    void handlePacket(DataInputStream inputStream, UUID connectionId) throws IOException {
        log.debug("Received packet of type: HANDSHAKE_PACKET");
        Server.getInstance().send(connectionId, new HandshakeResponsePacket());
    }

    @Override
    public Handler next() {
        return null;
    }
}
