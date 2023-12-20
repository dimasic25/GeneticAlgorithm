package com.demon2507.real_number.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.demon2507.Configuration.getConfig;

public class GenerationPool {
    public static final double CONVERGENCE_THRESHOLD = getConfig().getConvergentThreshold();

    private final List<Individ> individuals = new ArrayList<>();

    public GenerationPool(List<Individ> initialIndividuals) {
        Objects.requireNonNull(initialIndividuals);
        addIndividuals(initialIndividuals);
    }

    public List<Individ> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individ> individuals) {
        Objects.requireNonNull(individuals);

        this.individuals.clear();
        addIndividuals(individuals);
    }

    public void addIndividuals(Individ... individuals) {
        Objects.requireNonNull(individuals);

        addIndividuals(List.of(individuals));
    }

    public void addIndividuals(List<Individ> individuals) {
        Objects.requireNonNull(individuals);

        for (var individ : individuals) {
            addIndivid(individ);
        }
    }

    public void addIndivid(Individ individ) {
        individuals.add(Objects.requireNonNull(individ));
    }

    public int getSize() {
        return this.individuals.size();
    }

    public Individ getMostFitIndivid() {
        return this.individuals.stream()
                .max(Individ::compareTo)
                .orElseThrow(() -> new IllegalStateException("No most fit individ found in GenerationPool"));
    }

    public double getAverageFitness() {
        return individuals.stream()
                .mapToDouble(Individ::getFitness)
                .average()
                .orElseThrow(() -> new IllegalStateException("No average fitness found in Generation Pool"));
    }

    public boolean hasConverged() {
        return getMostFitIndivid().getFitness() - getAverageFitness() < CONVERGENCE_THRESHOLD;
    }
}
