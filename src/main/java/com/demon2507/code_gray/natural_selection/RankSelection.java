package com.demon2507.code_gray.natural_selection;

import com.demon2507.code_gray.model.GenerationPool;
import com.demon2507.code_gray.model.Individ;

import java.security.SecureRandom;
import java.util.*;

public class RankSelection implements NaturalSelectionStrategy {
    private final GenerationPool generationPool;

    public RankSelection(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public void filterGenerationPool() {
        List<Individ> individuals = generationPool.getIndividuals();
        individuals.sort(Comparator.comparingDouble(Individ::getFitness).reversed()); // Сортировка по убыванию фитнеса

        int populationSize = individuals.size();
        double totalRank = 0.5 * populationSize * (populationSize + 1);

        List<Individ> selectedIndividuals = new ArrayList<>();
        Random random = new SecureRandom();

        while (selectedIndividuals.size() < populationSize) {
            double randomValue = random.nextDouble();
            double cumulativeProbability = 0.0;

            for (int i = 0; i < populationSize; i++) {
                double probability = (i + 1) / totalRank;
                cumulativeProbability += probability;

                if (randomValue <= cumulativeProbability) {
                    selectedIndividuals.add(individuals.get(i));
                    break;
                }
            }
        }

        generationPool.setIndividuals(selectedIndividuals);
    }
}
