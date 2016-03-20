package rskupnik.edgar.networking.packethandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.executor.Executor;
import rskupnik.edgar.executor.tasks.InterpretCommandTask;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.io.DataInputStream;
import java.io.IOException;

final class CommandPacketHandler extends PacketHandler {

    private static final Logger logger = LogManager.getLogger(CommandPacketHandler.class);

    CommandPacketHandler() {

    }

    @Override
    void handlePacket(DataInputStream inputStream) throws IOException {
        logger.debug("Packet type: COMMAND_PACKET");
        String cmd = inputStream.readUTF();
        logger.debug("Command retrieved: " + cmd);
        Executor.getInstance().queue(new InterpretCommandTask(cmd));
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
