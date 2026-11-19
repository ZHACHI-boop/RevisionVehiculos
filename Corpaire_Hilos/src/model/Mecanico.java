package model;

import java.util.ArrayList;

public class Mecanico {

    private String codigo;
    private String nombre;
    private String marcaEspecialidad;
    private ArrayList<Vehiculo> loteVehiculos;

    public Mecanico(String codigo, String nombre, String marcaEspecialidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marcaEspecialidad = marcaEspecialidad;
        this.loteVehiculos = new ArrayList<Vehiculo>();
    }

    public void asignarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            this.loteVehiculos.add(vehiculo);
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarcaEspecialidad() {
        return marcaEspecialidad;
    }

    public ArrayList<Vehiculo> getLoteVehiculos() {
        return loteVehiculos;
    }
}