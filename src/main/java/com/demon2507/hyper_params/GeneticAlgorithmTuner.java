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

public class GeneticAlgorithmTuner {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmTuner.class);

    // запустить разные версии алгоритма с разными конфигурациями, отчет положить в csv-файл
    public void findBestAlgorithm() {
        var realAlgorithm = new com.demon2507.real_number.GeneticAlgorithm();
        var positionAlgorithm = new com.demon2507.code_gray.GeneticAlgorithm();
        List<Configuration> configurations = getConfigurations();
        List<StabilizedGeneticAlgorithmStatistics> realConfigStatistics = new ArrayList<>();
        List<StabilizedGeneticAlgorithmStatistics> positionCodeStatistics = new ArrayList<>();
        for (var config : configurations) {
            // ИЗМЕНЕНИЕ ГЛОБАЛЬНОЙ ПЕРЕМЕННОЙ
            Configuration.updateConfiguration(config);

            realConfigStatistics.add(realAlgorithm.solveStabilized());
            positionCodeStatistics.add(positionAlgorithm.solveStabilized());
        }
        new StatisticsExporter(null,null)
                .exportStabilizedResults(configurations, realConfigStatistics, positionCodeStatistics);
        log.info("{}", realConfigStatistics);
        log.info("{}", positionCodeStatistics);
    }

    private List<Configuration> getConfigurations() {
        List<Configuration> configurations = new ArrayList<>();
        // 50 individuals
        // метод рулетки, усечение, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.01));
        // метод рулетки, пропорциональный, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.01));
        // метод рулетки, ранговая, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.01));
        // метод рулетки, усечение, big mutation
        configurations.add((new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.1)));
        // метод рулетки, пропорциональный, big mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.1));
        // метод рулетки, ранговая, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.1));
        // инбридинг, усечение, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.01));
        // инбридинг, пропорциональный, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.01));
        // инбридинг, ранговая, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.01));
        // инбридинг, усечение big mutation
        configurations.add((new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.1)));
        // инбридинг, пропорциональный big mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.1));
        // инбридинг, ранговая big mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setIndividualsInPopulationCount(50)
                .setMutationRate(0.1));
        /// 200 individuals
        // метод рулетки, усечение, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setIndividualsInPopulationCount(200)
                .setMutationRate(0.01));
        // метод рулетки, пропорциональный, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setIndividualsInPopulationCount(200)
                .setMutationRate(0.01));
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setIndividualsInPopulationCount(200)
                .setMutationRate(0.01));
        // метод рулетки, усечение, big mutation
        configurations.add((new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setMutationRate(0.1)
                .setIndividualsInPopulationCount(200)));
        // метод рулетки, пропорциональный, big mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setMutationRate(0.1)
                .setIndividualsInPopulationCount(200));
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setMutationRate(0.1)
                .setIndividualsInPopulationCount(200));
        // инбридинг, усечение, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setIndividualsInPopulationCount(200)
                .setMutationRate(0.01));
        // инбридинг, пропорциональный, small mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setIndividualsInPopulationCount(200)
                .setMutationRate(0.01));
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setIndividualsInPopulationCount(200)
                .setMutationRate(0.01));
        // инбридинг, усечение big mutation
        configurations.add((new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
                .setMutationRate(0.1))
                .setIndividualsInPopulationCount(200));
        // инбридинг, пропорциональный big mutation
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.ROULETTE_WHEEL)
                .setMutationRate(0.1)
                .setIndividualsInPopulationCount(200));
        configurations.add(new Configuration()
                .setParentsSelectionStrategy(ParentSelectionStrategyType.INBREEDING)
                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.RANK)
                .setMutationRate(0.1)
                .setIndividualsInPopulationCount(200));

        return configurations;
    }

}
