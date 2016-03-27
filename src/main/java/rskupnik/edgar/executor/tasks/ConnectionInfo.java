package rskupnik.edgar.executor.tasks;

import java.util.UUID;

public abstract class ConnectionInfo implements Runnable {

    final UUID connectionId;

    public ConnectionInfo(UUID connectionId) {
        this.connectionId = connectionId;
    }
}
