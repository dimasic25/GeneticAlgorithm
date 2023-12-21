package com.demon2507.code_gray.model;

import com.demon2507.code_gray.util.DiscreteIntervalConverter;

import java.util.Objects;
import java.util.Random;

import static com.demon2507.Configuration.getConfig;
import static com.demon2507.common.Functions.deJong3;

public class Individ implements Comparable<Individ> {
    private final Random random = new Random();
    private static final DiscreteIntervalConverter xIntervalConverter = new DiscreteIntervalConverter(
            getConfig().getxInterval(), getConfig().getPrecision());
    private static final DiscreteIntervalConverter yIntervalConverter = new DiscreteIntervalConverter(
            getConfig().getyInterval(), getConfig().getPrecision());
    private static final DiscreteIntervalConverter zIntervalConverter = new DiscreteIntervalConverter(
            getConfig().getzInterval(), getConfig().getPrecision());

    private final StringBuilder geneticMaterialX = new StringBuilder();
    private final StringBuilder geneticMaterialY = new StringBuilder();
    private final StringBuilder geneticMaterialZ = new StringBuilder();

    private Double fitness;

    private Individ(String geneticMaterialX, String geneticMaterialY, String geneticMaterialZ) {
        this.geneticMaterialX.append(geneticMaterialX);
        this.geneticMaterialY.append(geneticMaterialY);
        this.geneticMaterialZ.append(geneticMaterialZ);
    }

    public static Individ fromGeneticMaterial(String geneticMaterialX, String geneticMaterialY, String geneticMaterialZ) {
        Objects.requireNonNull(geneticMaterialX);
        Objects.requireNonNull(geneticMaterialY);
        Objects.requireNonNull(geneticMaterialZ);

        // Преобразование в код Грея
        String grayCodeX = binaryToGray(geneticMaterialX);
        String grayCodeY = binaryToGray(geneticMaterialY);
        String grayCodeZ = binaryToGray(geneticMaterialZ);

        return new Individ(grayCodeX, grayCodeY, grayCodeZ);
    }

    private static String binaryToGray(String binaryCode) {
        StringBuilder grayCode = new StringBuilder();
        grayCode.append(binaryCode.charAt(0));

        for (int i = 1; i < binaryCode.length(); i++) {
            char currentBit = binaryCode.charAt(i);
            char previousBit = binaryCode.charAt(i - 1);

            // Инвертирование бита, если предыдущий бит равен текущему
            grayCode.append((currentBit == previousBit) ? '0' : '1');
        }

        return grayCode.toString();
    }

    public double getX() {
        return getXAsDouble();
    }

    public double getY() {
        return getYAsDouble();
    }

    public double getZ() {
        return getZAsDouble();
    }

    public String getGeneticMaterialX() {
        return geneticMaterialX.toString();
    }

    public String getGeneticMaterialY() {
        return geneticMaterialY.toString();
    }

    public String getGeneticMaterialZ() {
        return geneticMaterialZ.toString();
    }

    public double getFitness() {
        if (this.fitness == null) {
            // minus for minimization
            this.fitness = -deJong3(getXAsDouble(), getYAsDouble(), getZAsDouble());
        }
        return this.fitness;
    }

    public void mutate() {
        grayCodeMutation(geneticMaterialX);
        grayCodeMutation(geneticMaterialY);
        grayCodeMutation(geneticMaterialZ);
    }

    private void grayCodeMutation(StringBuilder geneticMaterial) {
        for (int i = 0; i < geneticMaterial.length(); i++) {
            if (random.nextDouble() < getConfig().getMutationRate()) {
                geneticMaterial.setCharAt(i, (geneticMaterial.charAt(i) == '0') ? '1' : '0');
            }
        }
    }

//    public void trySetXGen(int index, char bit) {
//        StringBuilder xCopy = new StringBuilder(geneticMaterialX);
//        xCopy.setCharAt(index, bit);
//        if (isValid(xCopy.toString(), geneticMaterialY.toString(), geneticMaterialZ.toString())) {
//            geneticMaterialX.setCharAt(index, bit);
//        }
//    }
//
//    public void tryInvertXGen(int index) {
//        char mutatedBit = (geneticMaterialX.charAt(index) == '0') ? '1' : '0';
//        trySetXGen(index, mutatedBit);
//    }
//
//    public void trySetYGen(int index, char bit) {
//        StringBuilder yCopy = new StringBuilder(geneticMaterialY);
//        yCopy.setCharAt(index, bit);
//        if (isValid(geneticMaterialX.toString(), yCopy.toString(), geneticMaterialZ.toString())) {
//            geneticMaterialY.setCharAt(index, bit);
//        }
//    }
//
//    public void tryInvertYGen(int index) {
//        char mutatedBit = (geneticMaterialY.charAt(index) == '0') ? '1' : '0';
//        trySetYGen(index, mutatedBit);
//    }
//
//    public void trySetZGen(int index, char bit) {
//        StringBuilder zCopy = new StringBuilder(geneticMaterialZ);
//        zCopy.setCharAt(index, bit);
//        if (isValid(geneticMaterialX.toString(), geneticMaterialY.toString(), zCopy.toString())) {
//            geneticMaterialY.setCharAt(index, bit);
//        }
//    }
//
//    public void tryInvertZGen(int index) {
//        char mutatedBit = (geneticMaterialZ.charAt(index) == '0') ? '1' : '0';
//        trySetYGen(index, mutatedBit);
//    }

    private double getXAsDouble() {
        return xIntervalConverter.positionToDouble(Integer.parseInt(grayToBinary(geneticMaterialX.toString()), 2));
    }

    private double getYAsDouble() {
        return yIntervalConverter.positionToDouble(Integer.parseInt(grayToBinary(geneticMaterialY.toString()), 2));
    }

    private double getZAsDouble() {
        return zIntervalConverter.positionToDouble(Integer.parseInt(grayToBinary(geneticMaterialZ.toString()), 2));
    }

    private String grayToBinary(String grayCode) {
        StringBuilder binary = new StringBuilder();
        int xorResult = 0;

        for (int i = 0; i < grayCode.length(); i++) {
            int grayBit = Character.getNumericValue(grayCode.charAt(i));
            xorResult ^= grayBit;
            binary.append(xorResult);
        }

        return binary.toString();
    }

    @Override
    public int compareTo(Individ other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }

    @Override
    public String toString() {
        return "%s %s %s (%s %s %s)".formatted(geneticMaterialX, geneticMaterialY, geneticMaterialZ,
                getXAsDouble(), getYAsDouble(), getZAsDouble());
    }

}
