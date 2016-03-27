package rskupnik.edgar.executor.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.executor.Executor;
import rskupnik.edgar.interpreter.CommandInterpreter;

import java.util.Optional;
import java.util.UUID;

public final class InterpretCommandTask extends ConnectionInfo {

    private static final Logger log = LogManager.getLogger(InterpretCommandTask.class);

    private final String command;

    public InterpretCommandTask(UUID connectionId, String command) {
        super(connectionId);
        this.command = command;
    }

    @Override
    public void run() {
        log.info("Interpreting command: "+command);
        Optional<String> output = CommandInterpreter.getInstance().interpret(command);
        log.info("Output: "+output.orElse("None"));
        if (output.isPresent()) {
            Executor.getInstance().queue(new SendOutputTask(connectionId, output.get()));
        }
    }
}
