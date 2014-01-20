package com.scarytom.logmaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Logmaker {
    private final static Logger LOGGER = LoggerFactory.getLogger(Logmaker.class);
    private static volatile boolean running = true;

    public static void main(String[] args) {
        LOGGER.info("Started...");

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override public void run() {
                LOGGER.info("Finishing...");
                running = false;
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    LOGGER.error("Failed to shut down", e);
                }
            }
        });

        long i = 0L;
        while(running) {
            LOGGER.info("count {}", i++);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                LOGGER.error("Failed to continue", e);
            }
        }
        LOGGER.info("Finished...");
    }
}
