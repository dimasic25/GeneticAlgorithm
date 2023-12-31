package com.demon2507.real_number.parent_selection;

import com.demon2507.real_number.model.GenerationPool;
import com.demon2507.real_number.model.Individ;
import com.demon2507.real_number.model.Parents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RouletteWheelSelectionStrategy implements ParentSelectionStrategy {
    private final GenerationPool generationPool;

    public RouletteWheelSelectionStrategy(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public Parents selectParents() {
        List<Individ> individuals = generationPool.getIndividuals();

        double totalFitness = individuals.stream()
                .mapToDouble(Individ::getFitness)
                .sum();

        List<Double> rouletteWheel = new ArrayList<>();
        double cumulativeFitness = 0;
        for (Individ individ : individuals) {
            cumulativeFitness += individ.getFitness() / totalFitness;
            rouletteWheel.add(cumulativeFitness);
        }

        Random random = new Random();
        Individ parent1 = spinRouletteWheel(rouletteWheel, random.nextDouble());
        Individ parent2 = spinRouletteWheel(rouletteWheel, random.nextDouble());

        return new Parents(parent1, parent2);
    }

    private Individ spinRouletteWheel(List<Double> rouletteWheel, double randomValue) {
        for (int i = 0; i < rouletteWheel.size(); i++) {
            if (randomValue <= rouletteWheel.get(i)) {
                return generationPool.getIndividuals().get(i);
            }
        }
        return generationPool.getIndividuals().get(rouletteWheel.size() - 1);
    }
}
