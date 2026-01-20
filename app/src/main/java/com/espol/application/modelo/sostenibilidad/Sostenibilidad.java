package com.espol.application.modelo.sostenibilidad;

import java.io.Serializable;
import java.util.ArrayList;


public class Sostenibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<RegistroSostenible> registros;

    public Sostenibilidad() {
        registros = new ArrayList<>();
    }

    public ArrayList<RegistroSostenible> getRegistros() {
        return registros;
    }


    private RegistroSostenible buscarPorFecha(String fechaIso) {
        for (RegistroSostenible r : registros) {
            if (r.getFechaIso().equals(fechaIso)) {
                return r;
            }
        }
        return null;
    }

    public void guardarRegistro(String fechaIso, ArrayList<String> acciones) {
        RegistroSostenible r = buscarPorFecha(fechaIso);
        if (r == null) {
            r = new RegistroSostenible(fechaIso);
            registros.add(r);
        }
        r.setAcciones(acciones);
    }

    public ArrayList<String> obtenerAcciones(String fechaIso) {
        RegistroSostenible r = buscarPorFecha(fechaIso);
        return (r == null) ? new ArrayList<>() : new ArrayList<>(r.getAcciones());
    }
}
