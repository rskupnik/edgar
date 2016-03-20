package rskupnik.edgar.executor.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class InterpretCommandTask implements Runnable {

    private static final Logger log = LogManager.getLogger(InterpretCommandTask.class);

    private final String command;

    public InterpretCommandTask(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        log.info("Interpreting command: "+command);
    }
}
