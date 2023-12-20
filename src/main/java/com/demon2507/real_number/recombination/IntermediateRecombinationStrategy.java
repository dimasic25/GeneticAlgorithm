package com.demon2507.real_number.recombination;

import com.demon2507.common.DoubleInterval;
import com.demon2507.common.Pair;
import com.demon2507.real_number.model.Individ;
import com.demon2507.real_number.model.Parents;
import com.demon2507.real_number.util.RandomUtils;

import java.util.Objects;

import static com.demon2507.Configuration.getConfig;

public class IntermediateRecombinationStrategy implements RecombinationStrategy {
    private static final int DIMENSIONS = getConfig().getDimensions();
    private static final double D = getConfig().getIntermediateDConstant();
    private static final DoubleInterval ALPHA_INTERVAL = new DoubleInterval(-D, 1 + D);
    private static final int PARENT_SIZE = 2;

    private final Parents parents;
    private final int geneticMaterialSize;

    public IntermediateRecombinationStrategy(Parents parents) {
        this.parents = Objects.requireNonNull(parents);
        this.geneticMaterialSize = parents.getFirstParent().getGeneticMaterial().length;
    }

    @Override
    public Pair<Individ> recombine() {
        double[][] generatedSchema = generateSchema();

        var parentGeneticMaterials = new double[][]{
                parents.getFirstParent().getGeneticMaterial(),
                parents.getSecondParent().getGeneticMaterial()
        };

        var childGeneticMaterials = new double[parentGeneticMaterials.length][parentGeneticMaterials[0].length];
        for (int i = 0; i < PARENT_SIZE; i++) {
            for (int j = 0; j < generatedSchema[0].length; j++) {
                childGeneticMaterials[i][j] = evalNewIndividGen(parentGeneticMaterials[0][j], parentGeneticMaterials[1][j], generatedSchema[i][j]);
            }
        }

        return new Pair<>(
                Individ.fromGeneticMaterial(childGeneticMaterials[0]),
                Individ.fromGeneticMaterial(childGeneticMaterials[1]));
    }

    private double[][] generateSchema() {
        double[][] schema = new double[DIMENSIONS][geneticMaterialSize];

        for (int i = 0; i < PARENT_SIZE; i++) {
            for (int j = 0; j < geneticMaterialSize; j++) {
                schema[i][j] = RandomUtils.generateRandomNumber(ALPHA_INTERVAL);
            }
        }
        return schema;
    }

    private double evalNewIndividGen(double firstParentGen, double secondParentGen, double alpha) {
        return firstParentGen + alpha * (secondParentGen - firstParentGen);
    }
}
