package com.mycompany.app.estudiantil.modelo.Hidratacionn;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class Hidratacion {
    
    private int metaDiaria;
    private List<RegistroHidratacion> registros;

    public Hidratacion() {
        this.metaDiaria = 2500;
        this.registros = new ArrayList<>();
    }

    public void registrarIngesta(int cantidadMl) {
        RegistroHidratacion r = new RegistroHidratacion(LocalDate.now(), LocalTime.now(), cantidadMl);
        registros.add(r);
    }

    public void establecerMeta(int nuevaMeta) {
        this.metaDiaria = nuevaMeta;
    }

    public int getMetaDiaria() { return metaDiaria; }

    public int getTotalHoy() {
        return registros.stream()
                .filter(r -> r.getFecha().equals(LocalDate.now()))
                .mapToInt(RegistroHidratacion::getCantidadAgua)
                .sum();
    }

    public List<RegistroHidratacion> getRegistrosDeHoy() {
        return registros.stream()
                .filter(r -> r.getFecha().equals(LocalDate.now()))
                  .collect(java.util.stream.Collectors.toList());
    }
}
