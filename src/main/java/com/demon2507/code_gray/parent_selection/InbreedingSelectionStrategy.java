package com.demon2507.code_gray.parent_selection;

import com.demon2507.code_gray.model.GenerationPool;
import com.demon2507.code_gray.model.Individ;
import com.demon2507.code_gray.model.Parents;

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
                String[] geneticMaterial1 = List.of(reference.getGeneticMaterialX(), reference.getGeneticMaterialY(), reference.getGeneticMaterialZ()).toArray(String[]::new);
                String[] geneticMaterial2 = List.of(neighbor.getGeneticMaterialX(), neighbor.getGeneticMaterialY(), neighbor.getGeneticMaterialZ()).toArray(String[]::new);
                int distance = calculateHammingDistance(geneticMaterial1, geneticMaterial2);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestNeighbor = neighbor;
                }
            }
        }

        return closestNeighbor;
    }

    // Метод для вычисления Хэммингова расстояния между двумя генетическими материалами
    private static int calculateHammingDistance(String[] geneticMaterial1, String[] geneticMaterial2) {
        if (geneticMaterial1.length != geneticMaterial2.length) {
            throw new IllegalArgumentException("Генетические материалы разной длины");
        }

        int distance = 0;
        for (int i = 0; i < geneticMaterial1.length; i++) {
            distance += countDifferentBits(geneticMaterial1[i], geneticMaterial2[i]);
        }

        return distance;
    }

    private static int countDifferentBits(String code1, String code2) {
        if (code1.length() != code2.length()) {
            throw new IllegalArgumentException("Коды Грея разной длины");
        }

        int count = 0;
        for (int i = 0; i < code1.length(); i++) {
            if (code1.charAt(i) != code2.charAt(i)) {
                count++;
            }
        }

        return count;
    }
}
