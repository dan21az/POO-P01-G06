package com.mycompany.app.estudiantil.modelo.sostenibilidad;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegistroSostenible {
    private LocalDate fecha;
    private ArrayList<String> acciones;
    private int puntos;

    public RegistroSostenible() {
        this.fecha = LocalDate.now();
        this.acciones = new ArrayList<>();
        this.puntos = 0;
    }
    public RegistroSostenible(LocalDate fecha) {
        this.fecha = fecha;
        this.acciones = new ArrayList<>();
        this.puntos = 0;
    }

    public void agregarAccion(String a) {
        acciones.add(a);
        puntos++;
    }

    public ArrayList<String> getAcciones() {
        return acciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getPuntos() {
        return puntos;
    }
}