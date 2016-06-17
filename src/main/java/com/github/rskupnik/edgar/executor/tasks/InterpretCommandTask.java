package com.github.rskupnik.edgar.executor.tasks;

import com.github.rskupnik.pigeon.commons.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.rskupnik.edgar.executor.Executor;
import com.github.rskupnik.edgar.interpreter.CommandInterpreter;

import java.util.Optional;
import java.util.UUID;

public final class InterpretCommandTask extends com.github.rskupnik.edgar.executor.tasks.ConnectionInfo {

    private static final Logger log = LogManager.getLogger(InterpretCommandTask.class);

    private final String command;

    public InterpretCommandTask(Connection connection, String command) {
        super(connection);
        this.command = command;
    }

    @Override
    public void run() {
        log.info("Interpreting command: "+command);
        Optional<String> output = CommandInterpreter.getInstance().interpret(command);
        log.info("Output: "+output.orElse("None"));
        if (output.isPresent()) {
            Executor.getInstance().queue(new SendOutputTask(connection, output.get()));
        }
    }
}
