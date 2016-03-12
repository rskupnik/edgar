package rskupnik.edgar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.parrot.Parrot;

import java.io.IOException;

public class Edgar {

    public static void main(String[] args) {
        new Edgar();
    }

    private static final Logger logger = LogManager.getLogger(Edgar.class);

    public Edgar() {
        logger.info("Launching Edgar...");
        loadConfiguration();

    }

    private void loadConfiguration() {
        try {
            Parrot.init();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(0);
        }
    }
}
