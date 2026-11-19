package main;

import java.io.IOException;
import java.util.ArrayList;

import controller.ArchivoUtil;
import controller.CargaServidorRunnable;
import controller.ProcesadorMecanicoThread;
import model.Mecanico;
import model.ResultadoLote;
import model.Vehiculo;
import view.ConsolaVista;

public class Main {

    public static void main(String[] args) {
        long inicioTiempo = System.currentTimeMillis();
        ConsolaVista vista = new ConsolaVista();
        vista.mostrarBannerInicio();

        try {
            ArchivoUtil.generarArchivosDinamicos();

            ArrayList<Mecanico> mecanicos = ArchivoUtil.leerMecanicos();
            ArrayList<Vehiculo> vehiculos = ArchivoUtil.leerVehiculos();


            vista.mostrarMensajeServidor("Solicitando reporte de gases de " + vehiculos.size() + " vehiculos...");
            CargaServidorRunnable tareaCarga = new CargaServidorRunnable(vehiculos);
            Thread hiloServidor = new Thread(tareaCarga, "Hilo-Servidor-Gases");
            hiloServidor.start();
            hiloServidor.join();
            vista.mostrarMensajeServidor("Datos recibidos correctamente en " + tareaCarga.getTiempoDemoraMs() + " ms.");


            asignarLotesPorMarca(mecanicos, vehiculos);


            ArrayList<ProcesadorMecanicoThread> hilosMecanicos = new ArrayList<ProcesadorMecanicoThread>();
            for (Mecanico m : mecanicos) {
                ProcesadorMecanicoThread hilo = new ProcesadorMecanicoThread(m);
                hilosMecanicos.add(hilo);
                hilo.start();
            }


            ArrayList<ResultadoLote> resultados = new ArrayList<ResultadoLote>();
            for (ProcesadorMecanicoThread hilo : hilosMecanicos) {
                hilo.join();
                ResultadoLote res = hilo.getResultado();
                if (res != null) {
                    resultados.add(res);
                    vista.mostrarTablaMecanico(res);
                }
            }

            long tiempoTotal = System.currentTimeMillis() - inicioTiempo;
            vista.mostrarResumenFinal(resultados, tiempoTotal);

        } catch (IOException e) {
            System.err.println("Error de lectura/escritura en archivos: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La ejecucion de los hilos fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void asignarLotesPorMarca(ArrayList<Mecanico> mecanicos, ArrayList<Vehiculo> vehiculos) {
        for (Mecanico m : mecanicos) {
            int contador = 0;
            for (Vehiculo v : vehiculos) {
                if (v.getMarca().equalsIgnoreCase(m.getMarcaEspecialidad()) && contador < 5) {
                    m.asignarVehiculo(v);
                    contador++;
                }
            }
        }
    }
}