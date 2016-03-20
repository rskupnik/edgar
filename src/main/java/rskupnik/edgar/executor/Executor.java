package rskupnik.edgar.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.parrot.Parrot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Executor {

    private static Executor INSTANCE;

    public static Executor getInstance() {
        if (INSTANCE == null)
            init();

        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new Executor();
    }

    private static final Logger log = LogManager.getLogger(Executor.class);

    private final int numberOfThreads = Integer.parseInt(Parrot.get("numberOfExecutors").orElse("2"));
    private final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

    private Executor() {
        log.info("Executor initialized with "+numberOfThreads+" threads in the pool");
    }

    public void queue(Runnable task) {
        executorService.execute(task);
    }
}
