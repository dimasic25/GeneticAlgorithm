package com.demon2507.hyper_params;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        var tuner = new GeneticAlgorithmTuner();
        tuner.findBestAlgorithm();
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("program execution time: {} minutes {} seconds",
                (elapsedTime / 1000) / 60,
                (elapsedTime / 1000) % 60);
    }
}
