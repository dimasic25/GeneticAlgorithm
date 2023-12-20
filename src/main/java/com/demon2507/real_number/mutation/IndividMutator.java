package com.demon2507.real_number.mutation;

import com.demon2507.real_number.model.Individ;

import java.security.SecureRandom;
import java.util.Random;

import static com.demon2507.Configuration.getConfig;

public class IndividMutator {
    private static final double MUTATION_RATE = getConfig().getMutationRate();
    private static final double PERTURBATION_RANGE = getConfig().getPerturbationRange();

    private final Individ[] individuals;

    public IndividMutator(Individ... individuals) {
        this.individuals = individuals;
    }

    public void mutate() {
        for (Individ individ : individuals) {
            mutateIndivid(individ);
        }
    }

    private void mutateIndivid(Individ individ) {
        Random random = new SecureRandom();
        int numberOfGenes = individ.getGeneticMaterial().length;

        if (random.nextDouble() < MUTATION_RATE) {
            int randomGeneIndex = random.nextInt(numberOfGenes);
            double mutatedValue = random.nextDouble() * 2 * PERTURBATION_RANGE - PERTURBATION_RANGE;
            individ.setGen(randomGeneIndex, mutatedValue);
        }
    }

}
