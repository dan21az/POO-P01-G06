package com.espol.application.modelo.sostenibilidad;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Representa el registro de acciones sostenibles de un día.
 */
public class RegistroSostenible implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fechaIso;              // yyyy-MM-dd
    private ArrayList<String> acciones;

    public RegistroSostenible(String fechaIso) {
        this.fechaIso = fechaIso;
        this.acciones = new ArrayList<>();
    }

    public String getFechaIso() {
        return fechaIso;
    }

    public ArrayList<String> getAcciones() {
        return acciones;
    }

    // Reemplaza completamente las acciones del día
    public void setAcciones(ArrayList<String> nuevas) {
        acciones.clear();
        if (nuevas != null) {
            acciones.addAll(nuevas);
        }
    }

    // Puntos = número de acciones realizadas
    public int getPuntos() {
        return acciones.size();
    }
}

