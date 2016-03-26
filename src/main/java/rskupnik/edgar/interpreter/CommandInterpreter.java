package rskupnik.edgar.interpreter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.scriptengine.*;
import rskupnik.edgar.scriptengine.ScriptEngine;

import java.util.Optional;

public final class CommandInterpreter {

    private static CommandInterpreter INSTANCE;

    public static CommandInterpreter getInstance() {
        if (INSTANCE == null)
            init();

        return INSTANCE;
    }

    private static final Logger logger = LogManager.getLogger(CommandInterpreter.class);

    private rskupnik.edgar.scriptengine.ScriptEngine scriptEngine;

    public static void init() {
        INSTANCE = new CommandInterpreter();
    }

    private CommandInterpreter() {
        logger.info("Initializing command interpreter...");
        scriptEngine = new ScriptEngine();
    }

    public Optional<String> interpret(String cmd) {
        Optional<Object> output = scriptEngine.handle(cmd);
        return output.isPresent() ? Optional.of((String) output.get()) : Optional.empty();
    }
}
