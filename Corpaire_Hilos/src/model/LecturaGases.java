package model;

public class LecturaGases {

    public static final double LIMITE_LAMBDA = 1.0;
    public static final double LIMITE_O2 = 2.0;
    public static final double LIMITE_N2O = 40.0;
    public static final double LIMITE_CO2 = 15.0;

    public static final double TOLERANCIA_LAMBDA = 70.0;
    public static final double TOLERANCIA_O2 = 30.0;
    public static final double TOLERANCIA_N2O = 80.0;
    public static final double TOLERANCIA_CO2 = 50.0;

    private double lambda;
    private double o2;
    private double n2o;
    private double co2;

    public LecturaGases(double lambda, double o2, double n2o, double co2) {
        this.lambda = lambda;
        this.o2 = o2;
        this.n2o = n2o;
        this.co2 = co2;
    }

    public boolean fallaLambda() {
        return lambda > LIMITE_LAMBDA;
    }

    public boolean fallaO2() {
        return o2 >= LIMITE_O2;
    }

    public boolean fallaN2O() {
        return n2o >= LIMITE_N2O;
    }

    public boolean fallaCO2() {
        return co2 >= LIMITE_CO2;
    }

    public int contarParametrosConFalla() {
        int fallas = 0;
        if (fallaLambda()) fallas++;
        if (fallaO2()) fallas++;
        if (fallaN2O()) fallas++;
        if (fallaCO2()) fallas++;
        return fallas;
    }

    public double getLambda() {
        return lambda;
    }

    public double getO2() {
        return o2;
    }

    public double getN2o() {
        return n2o;
    }

    public double getCo2() {
        return co2;
    }
}