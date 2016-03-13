package rskupnik.edgar.interpreter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CommandInterpreter {

    private static CommandInterpreter INSTANCE;

    public static CommandInterpreter getInstance() {
        if (INSTANCE == null)
            init();

        return INSTANCE;
    }

    private static final Logger logger = LogManager.getLogger(CommandInterpreter.class);

    private ScriptEngine scriptEngine;

    public static void init() {
        INSTANCE = new CommandInterpreter();
    }

    private CommandInterpreter() {
        logger.info("Initializing command interpreter...");
        scriptEngine = new ScriptEngine();
    }
}
