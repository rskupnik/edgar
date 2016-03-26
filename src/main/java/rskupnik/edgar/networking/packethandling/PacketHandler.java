package rskupnik.edgar.networking.packethandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public abstract class PacketHandler implements Handler {

    private static final Logger logger = LogManager.getLogger(PacketHandler.class);

    private static PacketHandler firstHandler= new CommandPacketHandler();

    public static void handle(int packetId, DataInputStream inputStream, UUID connectionId) {
        firstHandler.handle(new Object[] {packetId, inputStream, connectionId});  // Wrapping into Object[] in order for the compiler to recognize which handle() we want
    }

    @Override
    public Optional<Object> handle(Object... input) {
        try {
            if (input == null || input.length < 3)
                return next() == null ? Optional.empty() : next().handle(input);

            int packetId = (int) input[0];
            if (packetId != getHandledPacketId())
                return next() == null ? Optional.empty() : next().handle(input);

            handlePacket((DataInputStream) input[1], (UUID) input[2]);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    abstract int getHandledPacketId();

    abstract void handlePacket(DataInputStream inputStream, UUID connectionId) throws IOException;
}
