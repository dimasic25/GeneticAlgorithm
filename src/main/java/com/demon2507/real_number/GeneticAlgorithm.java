package com.demon2507.real_number;

import com.demon2507.common.GeneticAlgorithmStatistics;
import com.demon2507.common.Pair;
import com.demon2507.common.Point3D;
import com.demon2507.common.StabilizedGeneticAlgorithmStatistics;
import com.demon2507.real_number.model.GenerationPool;
import com.demon2507.real_number.model.Individ;
import com.demon2507.real_number.model.Parents;
import com.demon2507.real_number.mutation.IndividMutator;
import com.demon2507.real_number.natural_selection.NaturalSelectionStrategy;
import com.demon2507.real_number.natural_selection.RankSelection;
import com.demon2507.real_number.natural_selection.RouletteWheelSelection;
import com.demon2507.real_number.natural_selection.TruncationNaturalSelection;
import com.demon2507.real_number.parent_selection.InbreedingSelectionStrategy;
import com.demon2507.real_number.parent_selection.RouletteWheelSelectionStrategy;
import com.demon2507.real_number.parent_selection.ParentSelectionStrategy;
import com.demon2507.real_number.recombination.IntermediateRecombinationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static com.demon2507.Configuration.getConfig;
import static com.demon2507.real_number.util.RandomUtils.createRandomIndividuals;

public class GeneticAlgorithm {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithm.class);

    private final int reproductionsPerGenerationCount = (int) (getConfig().getRecombinationRate() * getConfig().getIndividualsInPopulationCount());

    private final GenerationPool generationPool = new GenerationPool(createRandomIndividuals(getConfig().getIndividualsInPopulationCount()));
    private int generationNumber = 0;
    private Individ bestIndivid;

    private static double calculateDistance(Point3D point1, Point3D point2) {
        double xDiff = point2.x() - point1.x();
        double yDiff = point2.y() - point1.y();
        double zDiff = point2.z() - point1.z();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public StabilizedGeneticAlgorithmStatistics solveStabilized() {
        var statistics = new ArrayList<GeneticAlgorithmStatistics>();

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
        return new StabilizedGeneticAlgorithmStatistics(averageGenerations, averageErrorRate, errorRuns);
    }

    private static double calculateAverage(List<GeneticAlgorithmStatistics> statistics, ToDoubleFunction<? super GeneticAlgorithmStatistics> statisticsParamGetter) {
        return statistics.stream()
                .mapToDouble(statisticsParamGetter)
                .average().orElseThrow();
    }

    public void solve() {
        while (!isStopCriteriaAcquired()) {
            for (int i = 0; i < reproductionsPerGenerationCount; i++) {
                Parents parents = getParentSelectionStrategy(generationPool)
                        .selectParents();

                Pair<Individ> children = new IntermediateRecombinationStrategy(parents)
                        .recombine();

                new IndividMutator(children.first(), children.second())
                        .mutate();

                if (children.first() != null) {
                    generationPool.addIndivid(children.first());
                }
                if (children.second() != null) {
                    generationPool.addIndivid(children.second());
                }
            }

            getNaturalSelectionStrategy(generationPool)
                    .filterGenerationPool();

            generationNumber++;

            Individ bestIndivid = generationPool.getMostFitIndivid();
            log.info("step {} most fit individ: {}, fit={}", generationNumber, bestIndivid, bestIndivid.getFitness());
        }

        this.bestIndivid = generationPool.getMostFitIndivid();
    }

    private boolean isStopCriteriaAcquired() {
        if (generationNumber == getConfig().getMaxGenerationsCount()) {
            return true;
        }

        if (generationPool.hasConverged()) {
            log.info("Covergence reached");
            return true;
        }

        return false;
    }

    private ParentSelectionStrategy getParentSelectionStrategy(GenerationPool pool) {
        var configStrategy = getConfig().getParentsSelectionStrategy();
        return switch (configStrategy) {
            case INBREEDING -> new InbreedingSelectionStrategy(pool);
            case ROULETTE_WHEEL -> new RouletteWheelSelectionStrategy(pool);
        };
    }

    private NaturalSelectionStrategy getNaturalSelectionStrategy(GenerationPool pool) {
        var configStrategy = getConfig().getNaturalSelectionStrategy();
        return switch (configStrategy) {
            case ROULETTE_WHEEL -> new RouletteWheelSelection(pool);
            case TRUNCATION -> new TruncationNaturalSelection(pool);
            case RANK -> new RankSelection(pool);
        };
    }

    public Individ getBestIndivid() {
        return bestIndivid;
    }

    private double getErrorRate(Individ target) {
        var solutions = new Point3D[]{
                new Point3D(0, 0, 0)
        };

        var targetPoint = new Point3D(target.getX(), target.getY(), target.getZ());
        var nearestPoint = solutions[0];
        double minDistance = calculateDistance(targetPoint, nearestPoint);

        for (int i = 1; i < solutions.length; i++) {
            double distance = calculateDistance(targetPoint, solutions[i]);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }

        return minDistance;
    }

    public GeneticAlgorithmStatistics getStatistics() {
        return new GeneticAlgorithmStatistics(generationNumber, getErrorRate(bestIndivid));
    }

}