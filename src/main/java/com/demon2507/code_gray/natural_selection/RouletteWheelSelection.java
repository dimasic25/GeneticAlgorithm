package com.demon2507.code_gray.natural_selection;

import com.demon2507.code_gray.model.GenerationPool;
import com.demon2507.code_gray.model.Individ;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RouletteWheelSelection implements NaturalSelectionStrategy {
    private final GenerationPool generationPool;

    public RouletteWheelSelection(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public void filterGenerationPool() {
        List<Individ> individuals = generationPool.getIndividuals();
        double totalFitness = individuals.stream().mapToDouble(Individ::getFitness).sum();

        List<Individ> selectedIndividuals = new ArrayList<>();
        Random random = new SecureRandom();

        while (selectedIndividuals.size() < generationPool.getSize()) {
            double randomValue = random.nextDouble();
            double cumulativeProbability = 0.0;

            for (Individ individ : individuals) {
                double probability = individ.getFitness() / totalFitness;
                cumulativeProbability += probability;

                if (randomValue <= cumulativeProbability) {
                    selectedIndividuals.add(individ);
                    break;
                }
            }
        }

        generationPool.setIndividuals(selectedIndividuals);
    }
}
