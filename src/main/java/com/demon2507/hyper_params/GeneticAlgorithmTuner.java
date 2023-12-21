package com.demon2507.hyper_params;

import com.demon2507.Configuration;
import com.demon2507.common.NaturalSelectionStrategyType;
import com.demon2507.common.ParentSelectionStrategyType;
import com.demon2507.common.StabilizedGeneticAlgorithmStatistics;
import com.demon2507.report.StatisticsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GeneticAlgorithmTuner {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmTuner.class);

    // запустить разные версии алгоритма с разными конфигурациями, отчет положить в csv-файл
    public void findBestAlgorithm() {
        var realAlgorithm = new com.demon2507.real_number.GeneticAlgorithm();
        var codeGrayAlgorithm = new com.demon2507.code_gray.GeneticAlgorithm();
        List<StabilizedGeneticAlgorithmStatistics> realConfigStatistics = new ArrayList<>();
        List<StabilizedGeneticAlgorithmStatistics> grayCodeStatistics = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(getUsingProcessorsCount());
        BlockingQueue<StabilizedGeneticAlgorithmStatistics> realConfigStatisticsQueue = new LinkedBlockingQueue<>();
        BlockingQueue<StabilizedGeneticAlgorithmStatistics> grayCodeStatisticsQueue = new LinkedBlockingQueue<>();

        List<Configuration> configurations = getConfigurations();
        for (Configuration config : configurations) {
            executor.submit(() -> {
                Configuration.updateConfiguration(config);
                try {
                    StabilizedGeneticAlgorithmStatistics realStatistics = realAlgorithm.solveStabilized(config);
                    StabilizedGeneticAlgorithmStatistics grayStatistics = codeGrayAlgorithm.solveStabilized(config);
                    // добавляет только те результаты, которые успешны (на случай невалидной конфигурации)
                    realConfigStatisticsQueue.put(realStatistics);
                    grayCodeStatisticsQueue.put(grayStatistics);
                } catch (Exception e) {
                    log.error("Algorithm error with config {}", config, e);
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        new StatisticsExporter(null,null)
                .exportStabilizedResults(configurations, new ArrayList<>(realConfigStatisticsQueue), new ArrayList<>(grayCodeStatisticsQueue));
        log.info("{}", realConfigStatistics);
        log.info("{}", grayCodeStatistics);
    }

    private static int getUsingProcessorsCount() {
        int numberOfProcessors = -1;
        return numberOfProcessors == -1
                ? Runtime.getRuntime().availableProcessors()
                : numberOfProcessors;
    }

    private List<Configuration> getConfigurations() {
        List<Configuration> configurations = new ArrayList<>();

        ParentSelectionStrategyType[] parentSelectionStrategies = {ParentSelectionStrategyType.INBREEDING, ParentSelectionStrategyType.ROULETTE_WHEEL};
        NaturalSelectionStrategyType[] naturalSelectionStrategies = {NaturalSelectionStrategyType.ROULETTE_WHEEL, NaturalSelectionStrategyType.RANK, NaturalSelectionStrategyType.TRUNCATION};
        double[] mutationRates = {0.01, 0.1};
        double[] recombinationRates = {0.1, 0.3, 0.5, 0.7, 0.9}; // Example values
        double[] perturbationRanges = {0.05, 0.1, 0.2}; // Example values
        double[] truncationThresholds = {0.3, 0.5, 0.7}; // Example values

        for (ParentSelectionStrategyType parentSelection : parentSelectionStrategies) {
            for (NaturalSelectionStrategyType naturalSelection : naturalSelectionStrategies) {
                for (double mutationRate : mutationRates) {
                    for (double recombinationRate : recombinationRates) {
                        for (double perturbationRange : perturbationRanges) {
                            for (double truncationThreshold : truncationThresholds) {
                                Configuration config = new Configuration()
                                        .setParentsSelectionStrategy(parentSelection)
                                        .setNaturalSelectionStrategy(naturalSelection)
                                        .setMutationRate(mutationRate)
                                        .setRecombinationRate(recombinationRate)
                                        .setPerturbationRange(perturbationRange);

                                if (naturalSelection == NaturalSelectionStrategyType.TRUNCATION) {
                                    config.setTruncationThreshold(truncationThreshold);
                                }

                                configurations.add(config);
                            }
                        }
                    }
                }
            }
        }
        return configurations;
    }

//    private List<Configuration> getConfigurations() {
//        List<Configuration> configurations = new ArrayList<>();
//        // 50 individuals
//        // метод рулетки, усечение, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // метод рулетки, пропорциональный, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // метод рулетки, ранговая, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // метод рулетки, усечение, big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1)));
//        // метод рулетки, пропорциональный, big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1));
//        // метод рулетки, ранговая, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1));
//        // инбридинг, усечение, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // инбридинг, пропорциональный, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // инбридинг, ранговая, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // инбридинг, усечение big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1)));
//        // инбридинг, пропорциональный big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1));
//        // инбридинг, ранговая big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1));
//        /// 200 individuals
//        // метод рулетки, усечение, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // метод рулетки, пропорциональный, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // метод рулетки, усечение, big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200)));
//        // метод рулетки, пропорциональный, big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200));
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200));
//        // инбридинг, усечение, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // инбридинг, пропорциональный, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // инбридинг, усечение big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setMutationRate(0.1))
//                .setIndividualsInPopulationCount(200));
//        // инбридинг, пропорциональный big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200));
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200));
//
//        return configurations;
//    }

}
