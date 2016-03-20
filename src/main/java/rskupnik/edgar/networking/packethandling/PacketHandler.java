package rskupnik.edgar.networking.packethandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class PacketHandler implements Handler {

    private static final Logger logger = LogManager.getLogger(PacketHandler.class);

    private static PacketHandler firstHandler= new CommandPacketHandler();

    public static void handle(int packetId, DataInputStream inputStream) {
        firstHandler.handle(new Object[] {packetId, inputStream});  // Wrapping into Object[] in order for the compiler to recognize which handle() we want
    }

    @Override
    public boolean handle(Object... input) {
        try {
            if (input == null || input.length < 2)
                return next() == null ? false : next().handle(input);

            int packetId = (int) input[0];
            if (packetId != getHandledPacketId())
                return next() == null ? false : next().handle(input);

            handlePacket((DataInputStream) input[1]);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return true;
    }

    abstract int getHandledPacketId();

    abstract void handlePacket(DataInputStream inputStream) throws IOException;
}
