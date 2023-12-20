package com.demon2507.real_number.parent_selection;

import com.demon2507.real_number.model.GenerationPool;
import com.demon2507.real_number.model.Individ;
import com.demon2507.real_number.model.Parents;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InbreedingSelectionStrategy implements ParentSelectionStrategy {
    private final GenerationPool generationPool;

    public InbreedingSelectionStrategy(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public Parents selectParents() {
        List<Individ> individuals = generationPool.getIndividuals();

        Random random = new SecureRandom();
        Individ parent = individuals.get(random.nextInt(individuals.size()));

        Individ parent2 = findClosestNeighbor(parent, individuals);

        return new Parents(parent, parent2);
    }

    private Individ findClosestNeighbor(Individ reference, List<Individ> individuals) {
        int minDistance = Integer.MAX_VALUE;
        Individ closestNeighbor = null;

        for (Individ neighbor : individuals) {
            if (!neighbor.equals(reference)) {
                int distance = calculateHammingDistance(reference.getGeneticMaterial(), neighbor.getGeneticMaterial());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestNeighbor = neighbor;
                }
            }
        }

        return closestNeighbor;
    }

    // Метод для вычисления Хэммингова расстояния между двумя генетическими материалами
    private int calculateHammingDistance(double[] geneticMaterial1, double[] geneticMaterial2) {
        if (geneticMaterial1.length != geneticMaterial2.length) {
            throw new IllegalArgumentException("Генетические материалы разной длины");
        }

        int distance = 0;
        for (int i = 0; i < geneticMaterial1.length; i++) {
            // Преобразование значений в битовую строку (например, через Double.doubleToRawLongBits())
            // и дальнейшее сравнение битов
            long bits1 = Double.doubleToRawLongBits(geneticMaterial1[i]);
            long bits2 = Double.doubleToRawLongBits(geneticMaterial2[i]);

            distance += countDifferentBits(bits1, bits2);
        }

        return distance;
    }

    private int countDifferentBits(long bits1, long bits2) {
        long xorResult = bits1 ^ bits2;
        int count = 0;
        while (xorResult != 0) {
            count += xorResult & 1;
            xorResult >>>= 1;
        }
        return count;
    }
}
