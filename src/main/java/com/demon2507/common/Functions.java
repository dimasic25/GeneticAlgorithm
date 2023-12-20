package com.demon2507.common;

public class Functions {

    public static double deJong3(double x, double y, double z) {
        double result = 0.0;

        result += Math.abs(Math.floor(x));
        result += Math.abs(Math.floor(y));
        result += Math.abs(Math.floor(z));

        return result;
    }
}
