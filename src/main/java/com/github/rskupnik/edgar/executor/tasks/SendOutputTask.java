package com.github.rskupnik.edgar.executor.tasks;

import com.github.rskupnik.edgar.networking.Network;
import com.github.rskupnik.pigeon.commons.Connection;
import com.github.rskupnik.pigeon.commons.exceptions.PigeonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.rskupnik.edgar.networking.packets.CommandOutputPacket;

public final class SendOutputTask extends com.github.rskupnik.edgar.executor.tasks.ConnectionInfo {

    private static final Logger log = LogManager.getLogger(SendOutputTask.class);

    private final String output;

    public SendOutputTask(Connection connection, String output) {
        super(connection);
        this.output = output;
    }

    @Override
    public void run() {
        log.debug("Sending output to connection ["+connection.getUuid()+"]: "+output);
        try {
            Network.getInstance().getServer().send(new CommandOutputPacket(output), connection);
        } catch (PigeonException e) {
            log.error(e.getMessage(), e);
        }
    }
}
