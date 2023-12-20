package com.demon2507.code_gray.util;

import com.demon2507.common.DoubleInterval;

public class DiscreteIntervalConverter {
    private final DoubleInterval doubleInterval;
    private final double step;

    public DiscreteIntervalConverter(DoubleInterval doubleInterval, double step) {
        this.doubleInterval = doubleInterval;
        this.step = step;
    }

    // Convert position number (index) to real number
    public double positionToDouble(int position) {
        if (position < 0 || position >= getNumberOfSteps()) {
            throw new IllegalArgumentException("Position number is out of bounds position=%s bounds=%s"
                    .formatted(position, getNumberOfSteps()));
        }

        return doubleInterval.start() + position * step;
    }

    // Convert real number to position number (index)
    public int realToPosition(double real) {
        if (real < doubleInterval.start() || real > doubleInterval.end()) {
            throw new IllegalArgumentException("Real number is out of bounds");
        }

        return (int) Math.floor((real - doubleInterval.start()) / step);
    }

    // Get the number of steps in the discrete interval
    public int getNumberOfSteps() {
        return (int) Math.floor((doubleInterval.end() - doubleInterval.start()) / step) + 1;
    }
}