package com.demon2507.code_gray.crossingover;

import com.demon2507.code_gray.model.Individ;
import com.demon2507.code_gray.model.Parents;
import com.demon2507.common.Pair;

import java.util.Objects;

public class TwoPointCrossoverStrategy implements RecombinationStrategy {
    private final Parents parents;

    public TwoPointCrossoverStrategy(Parents parents) {
        this.parents = Objects.requireNonNull(parents);
    }

    @Override
    public Pair<Individ> recombine() {
        Individ parent1 = parents.getFirstParent();
        Individ parent2 = parents.getSecondParent();

        String binaryGeneX1 = parent1.getGeneticMaterialX();
        String binaryGeneX2 = parent2.getGeneticMaterialX();
        String binaryGeneY1 = parent1.getGeneticMaterialY();
        String binaryGeneY2 = parent2.getGeneticMaterialY();
        String binaryGeneZ1 = parent1.getGeneticMaterialZ();
        String binaryGeneZ2 = parent2.getGeneticMaterialZ();

        // Генерируем две случайные точки для кроссовера
        int crossoverPoint1 = generateRandomCrossoverPoint(binaryGeneX1.length());
        int crossoverPoint2 = generateRandomCrossoverPoint(binaryGeneX1.length());

        // Убеждаемся, что crossoverPoint1 < crossoverPoint2
        if (crossoverPoint1 > crossoverPoint2) {
            int temp = crossoverPoint1;
            crossoverPoint1 = crossoverPoint2;
            crossoverPoint2 = temp;
        }

        // Создаем гены потомков для генетического материала X
        String childBinaryGeneX1 = binaryGeneX1.substring(0, crossoverPoint1) +
                binaryGeneX2.substring(crossoverPoint1, crossoverPoint2) +
                binaryGeneX1.substring(crossoverPoint2);
        String childBinaryGeneX2 = binaryGeneX2.substring(0, crossoverPoint1) +
                binaryGeneX1.substring(crossoverPoint1, crossoverPoint2) +
                binaryGeneX2.substring(crossoverPoint2);

        // Аналогично для генетического материала Y
        String childBinaryGeneY1 = binaryGeneY1.substring(0, crossoverPoint1) +
                binaryGeneY2.substring(crossoverPoint1, crossoverPoint2) +
                binaryGeneY1.substring(crossoverPoint2);
        String childBinaryGeneY2 = binaryGeneY2.substring(0, crossoverPoint1) +
                binaryGeneY1.substring(crossoverPoint1, crossoverPoint2) +
                binaryGeneY2.substring(crossoverPoint2);

        // Аналогично для генетического материала Z
        String childBinaryGeneZ1 = binaryGeneZ1.substring(0, crossoverPoint1) +
                binaryGeneZ2.substring(crossoverPoint1, crossoverPoint2) +
                binaryGeneZ1.substring(crossoverPoint2);
        String childBinaryGeneZ2 = binaryGeneZ2.substring(0, crossoverPoint1) +
                binaryGeneZ1.substring(crossoverPoint1, crossoverPoint2) +
                binaryGeneZ2.substring(crossoverPoint2);

        Individ child1;
        child1 = Individ.fromGeneticMaterial(childBinaryGeneX1, childBinaryGeneY1, childBinaryGeneZ1);
        child1.mutate();

        Individ child2;
        child2 = Individ.fromGeneticMaterial(childBinaryGeneX2, childBinaryGeneY2, childBinaryGeneZ2);
        child2.mutate();

        return new Pair<>(child1, child2);
    }

    private int generateRandomCrossoverPoint(int max) {
        return (int) (Math.random() * max);
    }
}