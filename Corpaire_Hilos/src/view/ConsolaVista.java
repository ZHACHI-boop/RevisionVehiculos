package view;

import java.util.ArrayList;

import model.LecturaGases;
import model.Mecanico;
import model.ResultadoLote;
import model.Vehiculo;

public class ConsolaVista {

    public void mostrarBannerInicio() {
        System.out.println("==========================================================================================");
        System.out.println("         SISTEMA DE REVISION VEHICULAR CORPAIRE - QUITO (GESTION CONCURRENTE)");
        System.out.println("==========================================================================================");
    }

    public void mostrarMensajeServidor(String mensaje) {
        System.out.println("[SERVIDOR DB] " + mensaje);
    }

    public void mostrarTablaMecanico(ResultadoLote res) {
        Mecanico mec = res.getMecanico();
        ArrayList<Vehiculo> autos = mec.getLoteVehiculos();

        System.out.println("\n+----------------------------------------------------------------------------------------+");
        System.out.printf("| MECANICO: %-22s | CODIGO: %-8s | MARCA ASIGNADA: %-18s |\n",
                mec.getNombre(), mec.getCodigo(), mec.getMarcaEspecialidad());
        System.out.println("+------------+--------------+------------+------------+------------+------------+----------+");
        System.out.println("| PLACA      | MARCA        | LAMBDA     | O2         | N2O        | CO2        | FALLAS   |");
        System.out.println("+------------+--------------+------------+------------+------------+------------+----------+");

        for (Vehiculo v : autos) {
            LecturaGases g = v.getLecturaGases();
            String strLambda = String.format("%.2f%s", g.getLambda(), g.fallaLambda() ? " (!)" : "");
            String strO2 = String.format("%.2f%s", g.getO2(), g.fallaO2() ? " (!)" : "");
            String strN2O = String.format("%.2f%s", g.getN2o(), g.fallaN2O() ? " (!)" : "");
            String strCO2 = String.format("%.2f%s", g.getCo2(), g.fallaCO2() ? " (!)" : "");

            System.out.printf("| %-10s | %-12s | %-10s | %-10s | %-10s | %-10s | %-8d |\n",
                    v.getPlaca(),
                    v.getMarca(),
                    strLambda,
                    strO2,
                    strN2O,
                    strCO2,
                    g.contarParametrosConFalla());
        }

        System.out.println("+------------+--------------+------------+------------+------------+------------+----------+");
        System.out.printf("| %% ERROR POR PARAMETRO -> Lambda: %.0f%% (Tol: 70%%) | O2: %.0f%% (Tol: 30%%)                     |\n",
                res.getPorcentajeErrorLambda(), res.getPorcentajeErrorO2());
        System.out.printf("|                          N2O:    %.0f%% (Tol: 80%%) | CO2: %.0f%% (Tol: 50%%)                    |\n",
                res.getPorcentajeErrorN2O(), res.getPorcentajeErrorCO2());
        System.out.printf("| %% ERROR GENERAL DEL LOTE (5 autos / 20 mediciones): %.2f%%                              |\n",
                res.getPorcentajeErrorGeneral());
        System.out.printf("| RESULTADO DEL LOTE: %-66s |\n", res.getMensajeEstado().toUpperCase());
        System.out.printf("| OBSERVACION:        %-66s |\n", res.getDetalleCondicion());
        System.out.println("+----------------------------------------------------------------------------------------+");
    }

    public void mostrarResumenFinal(ArrayList<ResultadoLote> resultados, long tiempoTotalMs) {
        int aprobados = 0;
        for (ResultadoLote r : resultados) {
            if (r.isAprobadoPrimeraRevision()) {
                aprobados++;
            }
        }
        int reprobados = resultados.size() - aprobados;

        System.out.println("\n==========================================================================================");
        System.out.println(" RESUMEN GENERAL DE JORNADA");
        System.out.println(" - Total mecanicos procesados: " + resultados.size());
        System.out.println(" - Lotes aprobados en Primera Revision: " + aprobados);
        System.out.println(" - Lotes con Segunda Revision Asignada: " + reprobados);
        System.out.println(" - Tiempo total de ejecucion concurrente: " + tiempoTotalMs + " ms");
        System.out.println("==========================================================================================");
    }
}