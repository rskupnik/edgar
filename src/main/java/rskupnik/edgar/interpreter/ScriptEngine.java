package rskupnik.edgar.interpreter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class ScriptEngine {

    private static final Logger logger = LogManager.getLogger(ScriptEngine.class);

    ScriptEngine() {
        logger.info("Initializing script engine...");
    }
}
