package com.github.rskupnik.edgar;

import com.github.rskupnik.edgar.networking.Network;
import com.github.rskupnik.pigeon.commons.exceptions.PigeonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.rskupnik.edgar.executor.Executor;
import com.github.rskupnik.edgar.interpreter.CommandInterpreter;
import com.github.rskupnik.edgar.other.Config;

public final class Edgar {

    public static void main(String[] args) {
        new Edgar();
    }

    private static final Logger logger = LogManager.getLogger(Edgar.class);

    public Edgar() {
        logger.info("Launching Edgar...");
        loadConfiguration();
        CommandInterpreter.init();
        Executor.init();
        try {
            Network.getInstance().launch();
        } catch (PigeonException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void loadConfiguration() {
        Config.init();
    }
}
