package com.mycompany.app.estudiantil.modelo.Sostenibilidad;
import java.util.ArrayList;

public class Sostenibilidad {
    private ArrayList<RegistroSostenible> registros;

    public Sostenibilidad() {
        registros = new ArrayList<>();
    }

    public void agregarRegistro(RegistroSostenible r) {
        registros.add(r);
    }

    public ArrayList<RegistroSostenible> getRegistros() {
        return registros;
    }
}

