package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Mecanico;
import model.Vehiculo;

public class ArchivoUtil {

    public static final String CARPETA_DATA = "data";
    public static final String RUTA_MECANICOS = CARPETA_DATA + File.separator + "mecanicos.txt";
    public static final String RUTA_VEHICULOS = CARPETA_DATA + File.separator + "vehiculos.txt";


    private static final Pattern REGEX_MECANICO =
            Pattern.compile("^(MEC-\\d{2});([A-Za-z ]{3,40});([A-Za-z0-9 -]{2,25})$");

    private static final Pattern REGEX_VEHICULO =
            Pattern.compile("^([A-Za-z0-9 -]{2,25});([A-Z]{3}-\\d{4})$");

    private static final String[] MARCAS = {
        "Toyota", "Chevrolet", "Hyundai", "Kia", "Nissan",
        "Mazda", "Ford", "Volkswagen", "Renault", "Suzuki"
    };

    private static final String[] NOMBRES_MECANICOS = {
        "Carlos Andrade", "Luis Morales", "Jorge Salazar", "Diego Herrera", "Marco Tipan",
        "Andres Caiza", "Pedro Zambrano", "Kevin Guaman", "Cristian Viteri", "Fernando Paredes"
    };

    public static void generarArchivosDinamicos() throws IOException {
        File carpeta = new File(CARPETA_DATA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_MECANICOS))) {
            for (int i = 0; i < 10; i++) {
                String codigo = String.format("MEC-%02d", (i + 1));
                bw.write(codigo + ";" + NOMBRES_MECANICOS[i] + ";" + MARCAS[i]);
                bw.newLine();
            }
        }


        Random random = new Random();
        ArrayList<String> placasUsadas = new ArrayList<String>();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_VEHICULOS))) {
            for (int i = 0; i < MARCAS.length; i++) {
                String marca = MARCAS[i];
                for (int j = 0; j < 5; j++) {
                    String placa = generarPlacaUnica(random, placasUsadas);
                    bw.write(marca + ";" + placa);
                    bw.newLine();
                }
            }
        }
    }

    private static String generarPlacaUnica(Random random, ArrayList<String> placasUsadas) {
        String letrasPrefijo = "PGBALMC";
        String placa;
        do {
            char c1 = letrasPrefijo.charAt(random.nextInt(letrasPrefijo.length()));
            char c2 = (char) ('A' + random.nextInt(26));
            char c3 = (char) ('A' + random.nextInt(26));
            int numero = 1000 + random.nextInt(9000);
            placa = "" + c1 + c2 + c3 + "-" + numero;
        } while (placasUsadas.contains(placa));

        placasUsadas.add(placa);
        return placa;
    }

    public static ArrayList<Mecanico> leerMecanicos() throws IOException {
        ArrayList<Mecanico> lista = new ArrayList<Mecanico>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_MECANICOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Matcher matcher = REGEX_MECANICO.matcher(linea.trim());
                if (matcher.matches()) {
                    String codigo = matcher.group(1);
                    String nombre = matcher.group(2);
                    String marca = matcher.group(3);
                    lista.add(new Mecanico(codigo, nombre, marca));
                }
            }
        }
        return lista;
    }

    public static ArrayList<Vehiculo> leerVehiculos() throws IOException {
        ArrayList<Vehiculo> lista = new ArrayList<Vehiculo>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_VEHICULOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Matcher matcher = REGEX_VEHICULO.matcher(linea.trim());
                if (matcher.matches()) {
                    String marca = matcher.group(1);
                    String placa = matcher.group(2);
                    lista.add(new Vehiculo(placa, marca));
                }
            }
        }
        return lista;
    }
}