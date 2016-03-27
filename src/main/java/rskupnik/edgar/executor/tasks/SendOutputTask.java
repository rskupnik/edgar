package rskupnik.edgar.executor.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.networking.Server;
import rskupnik.edgar.networking.packethandling.packets.CommandOutputPacket;

import java.util.UUID;

public class SendOutputTask extends ConnectionInfo {

    private static final Logger log = LogManager.getLogger(SendOutputTask.class);

    private final String output;

    public SendOutputTask(UUID connectionId, String output) {
        super(connectionId);
        this.output = output;
    }

    @Override
    public void run() {
        log.debug("Sending output to connection ["+connectionId+"]: "+output);
        Server.getInstance().send(connectionId, new CommandOutputPacket(output));
    }
}
