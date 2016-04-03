package rskupnik.edgar.networking.packethandling.receive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.executor.Executor;
import rskupnik.edgar.executor.tasks.InterpretCommandTask;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

final class CommandPacketHandler extends PacketHandler {

    private static final Logger logger = LogManager.getLogger(CommandPacketHandler.class);

    CommandPacketHandler() {

    }

    @Override
    void handlePacket(DataInputStream inputStream, UUID connectionId) throws IOException {
        logger.debug("Received packet of type: COMMAND_PACKET");
        String cmd = inputStream.readUTF();
        logger.debug("Command retrieved: " + cmd);
        Executor.getInstance().queue(new InterpretCommandTask(connectionId, cmd));
    }

    @Override
    int getHandledPacketId() {
        return PacketId.COMMAND_PACKET;
    }

    @Override
    public Handler next() {
        return new HandshakePacketHandler();
    }
}
