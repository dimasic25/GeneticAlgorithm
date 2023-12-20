package com.demon2507.real_number.model;

import java.util.Arrays;
import java.util.Objects;

import static com.demon2507.Configuration.getConfig;
import static com.demon2507.common.Functions.deJong3;

public class Individ implements Comparable<Individ> {
    private final double[] geneticMaterial;
    private Double fitness;

    private Individ(double[] geneticMaterial) {
        this.geneticMaterial = geneticMaterial;
    }

    public static Individ fromGeneticMaterial(double[] geneticMaterial) {
        Objects.requireNonNull(geneticMaterial);
        if (geneticMaterial.length != getConfig().getDimensions()) {
            throw new IllegalArgumentException("Invalid dimensions passed while construct Individ instance %s"
                    .formatted(Arrays.toString(geneticMaterial)));
        }
        return new Individ(geneticMaterial);
    }

    public void setGen(int index, Double genValue) {
        this.geneticMaterial[index] = Objects.requireNonNull(genValue);
    }

    public double getFitness() {
        if (this.fitness == null) {
            this.fitness = -deJong3(geneticMaterial[0], geneticMaterial[1], geneticMaterial[2]);
        }
        return this.fitness;
    }

    public double getX() {
        return geneticMaterial[0];
    }

    public double getY() {
        return geneticMaterial[1];
    }

    public double getZ() { return geneticMaterial[2]; }

    public double[] getGeneticMaterial() {
        return this.geneticMaterial;
    }

    @Override
    public int compareTo(Individ other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }

    @Override
    public String toString() {
        return Arrays.toString(geneticMaterial);
    }
}
