package com.demon2507;

import com.demon2507.common.DoubleInterval;
import com.demon2507.common.NaturalSelectionStrategyType;
import com.demon2507.common.ParentSelectionStrategyType;

import java.util.Objects;

public class Configuration {
    // Размерность задачи
    private int dimensions = 3;
    // Количество особей в популяции
    private int individualsInPopulationCount = 50;
    // Доля рекомбинаций в зависимости от от общего количества популяции, которая должна размножиться в одном поколении
    private double recombinationRate = 0.7;
    // Максимальное количество поколений
    private int maxGenerationsCount = 40;
    // Порог конвергентности, нужен для определения схожести поколения и преждевременной остановки алгоритма
    private double convergentThreshold = 0.000_001;
    // Вероятность мутации
    private double mutationRate = 0.01;
    // Шаг мутации в алгоритмах с вещественными генами
    private double perturbationRange = 5.12;
    // Интервал, на котором будет происходит поиск по X
    private DoubleInterval xInterval = new DoubleInterval(-5.12, 5.12);
    // Интервал, на котором будет происходит поиск по Y
    private DoubleInterval yInterval = new DoubleInterval(-5.12, 5.12);
    // Интервал, на котором будет происходит поиск по Z
    private DoubleInterval zInterval = new DoubleInterval(-5.12, 5.12);
    // Точность, которая достигается делением интервала на части. Используется при кодировании чисел с плавающей точкой
    private double precision = 0.000_001;
    // Константа d, используется в алгоритме промежуточной рекомбинации для вещественых генов
    private double intermediateDConstant = 0.25;
    // Доля усеченных особей от всей популяции. Используется в алгоритме селекции методом усечения.
    private double truncationThreshold = 0.5;
    // Количество прогонов одного алгоритма
    private int numberOfRuns = 50;
    // величина ошибки, которая будет считаться за неудачный запуск алгоритма
    private double errorThreshold = 0.5;

    private ParentSelectionStrategyType parentsSelectionStrategy = ParentSelectionStrategyType.ROULETTE_WHEEL;
    private NaturalSelectionStrategyType naturalSelectionStrategy = NaturalSelectionStrategyType.ROULETTE_WHEEL;

    public Configuration() {
    }

    private static Configuration INSTANCE = new Configuration();

    public static void updateConfiguration(Configuration newConfig) {
        INSTANCE = newConfig;
    }

    public static Configuration getConfig() {
        return INSTANCE;
    }

    public int getDimensions() {
        return this.dimensions;
    }

    public Configuration setDimensions(int dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public int getIndividualsInPopulationCount() {
        return this.individualsInPopulationCount;
    }

    public Configuration setIndividualsInPopulationCount(int individualsInPopulationCount) {
        this.individualsInPopulationCount = individualsInPopulationCount;
        return this;
    }

    public double getRecombinationRate() {
        return this.recombinationRate;
    }

    public Configuration setRecombinationRate(double recombinationRate) {
        this.recombinationRate = recombinationRate;
        return this;
    }

    public int getMaxGenerationsCount() {
        return this.maxGenerationsCount;
    }

    public Configuration setMaxGenerationsCount(int maxGenerationsCount) {
        this.maxGenerationsCount = maxGenerationsCount;
        return this;
    }

    public double getConvergentThreshold() {
        return this.convergentThreshold;
    }

    public Configuration setConvergentThreshold(double convergentThreshold) {
        this.convergentThreshold = convergentThreshold;
        return this;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    public Configuration setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
        return this;
    }

    public double getPerturbationRange() {
        return this.perturbationRange;
    }

    public Configuration setPerturbationRange(double perturbationRange) {
        this.perturbationRange = perturbationRange;
        return this;
    }

    public DoubleInterval getxInterval() {
        return this.xInterval;
    }

    public Configuration setxInterval(DoubleInterval xInterval) {
        this.xInterval = xInterval;
        return this;
    }

    public DoubleInterval getyInterval() {
        return this.yInterval;
    }

    public Configuration setyInterval(DoubleInterval yInterval) {
        this.yInterval = yInterval;
        return this;
    }

    public DoubleInterval getzInterval() {
        return zInterval;
    }

    public Configuration setzInterval(DoubleInterval zInterval) {
        this.zInterval = zInterval;
        return this;
    }

    public double getPrecision() {
        return this.precision;
    }

    public Configuration setPrecision(double precision) {
        this.precision = precision;
        return this;
    }

    public double getIntermediateDConstant() {
        return this.intermediateDConstant;
    }

    public Configuration setIntermediateDConstant(double intermediateDConstant) {
        this.intermediateDConstant = intermediateDConstant;
        return this;
    }

    public double getTruncationThreshold() {
        return this.truncationThreshold;
    }

    public Configuration setTruncationThreshold(double truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
        return this;
    }

    public int getNumberOfRuns() {
        return this.numberOfRuns;
    }

    public Configuration setNumberOfRuns(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
        return this;
    }

    public double getErrorThreshold() {
        return this.errorThreshold;
    }

    public Configuration setErrorThreshold(double errorThreshold) {
        this.errorThreshold = errorThreshold;
        return this;
    }

    public ParentSelectionStrategyType getParentsSelectionStrategy() {
        return this.parentsSelectionStrategy;
    }

    public Configuration setParentsSelectionStrategy(ParentSelectionStrategyType parentsSelectionStrategy) {
        this.parentsSelectionStrategy = parentsSelectionStrategy;
        return this;
    }

    public NaturalSelectionStrategyType getNaturalSelectionStrategy() {
        return this.naturalSelectionStrategy;
    }

    public Configuration setNaturalSelectionStrategy(NaturalSelectionStrategyType naturalSelectionStrategy) {
        this.naturalSelectionStrategy = naturalSelectionStrategy;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return dimensions == that.dimensions
                && individualsInPopulationCount == that.individualsInPopulationCount
                && Double.compare(recombinationRate, that.recombinationRate) == 0
                && maxGenerationsCount == that.maxGenerationsCount
                && Double.compare(convergentThreshold, that.convergentThreshold) == 0
                && Double.compare(mutationRate, that.mutationRate) == 0
                && Double.compare(perturbationRange, that.perturbationRange) == 0
                && Double.compare(precision, that.precision) == 0
                && Double.compare(intermediateDConstant, that.intermediateDConstant) == 0
                && Double.compare(truncationThreshold, that.truncationThreshold) == 0
                && numberOfRuns == that.numberOfRuns && Objects.equals(xInterval, that.xInterval)
                && Objects.equals(yInterval, that.yInterval)
                && Double.compare(errorThreshold, that.errorThreshold) == 0
                && parentsSelectionStrategy.compareTo(that.parentsSelectionStrategy) == 0
                && naturalSelectionStrategy.compareTo(that.naturalSelectionStrategy) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimensions, individualsInPopulationCount, recombinationRate, maxGenerationsCount,
                convergentThreshold, mutationRate, perturbationRange, xInterval, yInterval, precision, intermediateDConstant,
                truncationThreshold, numberOfRuns, errorThreshold, parentsSelectionStrategy, naturalSelectionStrategy);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "dimensions=" + dimensions +
                ", individualsInPopulationCount=" + individualsInPopulationCount +
                ", recombinationRate=" + recombinationRate +
                ", maxGenerationsCount=" + maxGenerationsCount +
                ", convergentThreshold=" + convergentThreshold +
                ", mutationRate=" + mutationRate +
                ", perturbationRange=" + perturbationRange +
                ", xInterval=" + xInterval +
                ", yInterval=" + yInterval +
                ", precision=" + precision +
                ", intermediateDConstant=" + intermediateDConstant +
                ", truncationThreshold=" + truncationThreshold +
                ", numberOfRuns=" + numberOfRuns +
                ", errorThreshold=" + errorThreshold +
                ", parentsSelectionStrategy=" + parentsSelectionStrategy +
                ", naturalSelectionStrategy=" + naturalSelectionStrategy +
                '}';
    }

}
