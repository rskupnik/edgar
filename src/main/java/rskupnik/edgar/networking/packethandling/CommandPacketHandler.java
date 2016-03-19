package rskupnik.edgar.networking.packethandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.io.DataInputStream;
import java.io.IOException;

public final class CommandPacketHandler extends PacketHandler {

    private static final Logger logger = LogManager.getLogger(CommandPacketHandler.class);

    CommandPacketHandler() {

    }

    @Override
    void handlePacket(DataInputStream inputStream) throws IOException {
        logger.debug("Packet type: COMMAND_PACKET");
        String cmd = inputStream.readUTF();
        logger.debug("Command retrieved: "+cmd);
    }

    @Override
    int getHandledPacketId() {
        return PacketId.COMMAND_PACKET;
    }

    @Override
    public Handler next() {
        return null;
    }
}
