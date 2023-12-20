package com.demon2507.code_gray;

import com.demon2507.common.GeneticAlgorithmStatistics;
import com.demon2507.report.StatisticsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static com.demon2507.Configuration.getConfig;

public class MainCodeGrayTest {
    private static final Logger log = LoggerFactory.getLogger(MainCodeGrayTest.class);

    public static void main(String[] args) {
        var statistics = new ArrayList<GeneticAlgorithmStatistics>();
        var config = getConfig();

        for (int i = 0; i < getConfig().getNumberOfRuns(); i++) {
            log.info("\nRUN#{}", i + 1);
            var geneticAlgorithm = new GeneticAlgorithm();
            geneticAlgorithm.solve();
            statistics.add(geneticAlgorithm.getStatistics());
        }

        log.info("=====Algo statistics=====");
        int averageGenerations = (int) calculateAverage(statistics, GeneticAlgorithmStatistics::generationNumberCount);
        double averageErrorRate = calculateAverage(statistics, GeneticAlgorithmStatistics::errorRate);
        int errorRuns = (int) statistics.stream().filter(stat -> stat.errorRate() > 0.5).count();
        log.info("Average number of generation to find optimum: {}", averageGenerations);
        log.info("Mean error rate: {}", averageErrorRate);
        log.info("Error runs: {}", errorRuns);
        new StatisticsExporter(config, "position_code")
                .export(averageGenerations, averageErrorRate);
    }

    private static double calculateAverage(List<GeneticAlgorithmStatistics> statistics, ToDoubleFunction<? super GeneticAlgorithmStatistics> statisticsParamGetter) {
        return statistics.stream()
                .mapToDouble(statisticsParamGetter)
                .average().orElseThrow();
    }
}