package rskupnik.edgar.executor.tasks;

import java.util.UUID;

public abstract class InitiatedByConnectionTask implements Runnable {

    private final UUID connectionId;

    public InitiatedByConnectionTask(UUID connectionId) {
        this.connectionId = connectionId;
    }
}
