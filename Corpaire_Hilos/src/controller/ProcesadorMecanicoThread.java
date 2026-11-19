package controller;

import java.util.ArrayList;

import model.LecturaGases;
import model.Mecanico;
import model.ResultadoLote;
import model.Vehiculo;

public class ProcesadorMecanicoThread extends Thread {

    private Mecanico mecanico;
    private ResultadoLote resultado;

    public ProcesadorMecanicoThread(Mecanico mecanico) {
        super("Hilo-" + mecanico.getCodigo());
        this.mecanico = mecanico;
    }

    @Override
    public void run() {
        ArrayList<Vehiculo> lote = mecanico.getLoteVehiculos();
        int totalAutos = lote.size();

        if (totalAutos == 0) {
            return;
        }

        int fallasLambda = 0;
        int fallasO2 = 0;
        int fallasN2O = 0;
        int fallasCO2 = 0;
        int totalParametrosEvaluados = totalAutos * 4;
        int totalParametrosFallidos = 0;

        for (Vehiculo v : lote) {
            LecturaGases g = v.getLecturaGases();
            if (g != null) {
                if (g.fallaLambda()) fallasLambda++;
                if (g.fallaO2()) fallasO2++;
                if (g.fallaN2O()) fallasN2O++;
                if (g.fallaCO2()) fallasCO2++;
                totalParametrosFallidos += g.contarParametrosConFalla();
            }
        }

        double errLambda = (fallasLambda * 100.0) / totalAutos;
        double errO2 = (fallasO2 * 100.0) / totalAutos;
        double errN2O = (fallasN2O * 100.0) / totalAutos;
        double errCO2 = (fallasCO2 * 100.0) / totalAutos;
        double errGeneral = (totalParametrosFallidos * 100.0) / totalParametrosEvaluados;

        ArrayList<String> motivosReprobacion = new ArrayList<String>();

        if (errLambda > LecturaGases.TOLERANCIA_LAMBDA) {
            motivosReprobacion.add(String.format("Lambda (%.0f%% > %.0f%%)", errLambda, LecturaGases.TOLERANCIA_LAMBDA));
        }
        if (errO2 > LecturaGases.TOLERANCIA_O2) {
            motivosReprobacion.add(String.format("O2 (%.0f%% > %.0f%%)", errO2, LecturaGases.TOLERANCIA_O2));
        }
        if (errN2O > LecturaGases.TOLERANCIA_N2O) {
            motivosReprobacion.add(String.format("N2O (%.0f%% > %.0f%%)", errN2O, LecturaGases.TOLERANCIA_N2O));
        }
        if (errCO2 > LecturaGases.TOLERANCIA_CO2) {
            motivosReprobacion.add(String.format("CO2 (%.0f%% > %.0f%%)", errCO2, LecturaGases.TOLERANCIA_CO2));
        }

        boolean aprobado = motivosReprobacion.isEmpty();
        String mensajeEstado = aprobado ? "Pasa en Primera Revision" : "Segunda Revision Asignada";
        String detalle = aprobado
                ? "Todos los parametros estan dentro del % de tolerancia."
                : "Supera tolerancia en: " + String.join(", ", motivosReprobacion);

        this.resultado = new ResultadoLote(
                mecanico,
                errLambda,
                errO2,
                errN2O,
                errCO2,
                errGeneral,
                aprobado,
                mensajeEstado,
                detalle
        );
    }

    public ResultadoLote getResultado() {
        return resultado;
    }
}