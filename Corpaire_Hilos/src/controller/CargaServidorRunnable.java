package controller;

import java.util.ArrayList;
import java.util.Random;

import model.LecturaGases;
import model.Vehiculo;

public class CargaServidorRunnable implements Runnable {

    private ArrayList<Vehiculo> vehiculos;
    private int tiempoDemoraMs;

    public CargaServidorRunnable(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public void run() {
        Random random = new Random();

        this.tiempoDemoraMs = 500 + random.nextInt(2501);

        try {
            Thread.sleep(tiempoDemoraMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Vehiculo v : vehiculos) {
            double lambda = redondear(0.75 + (random.nextDouble() * 0.65)); // 0.75 a 1.40
            double o2 = redondear(0.40 + (random.nextDouble() * 2.60));     // 0.40 a 3.00
            double n2o = redondear(15.0 + (random.nextDouble() * 45.0));    // 15.0 a 60.0
            double co2 = redondear(8.0 + (random.nextDouble() * 12.0));     // 8.0 a 20.0

            v.setLecturaGases(new LecturaGases(lambda, o2, n2o, co2));
        }
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public int getTiempoDemoraMs() {
        return tiempoDemoraMs;
    }
}