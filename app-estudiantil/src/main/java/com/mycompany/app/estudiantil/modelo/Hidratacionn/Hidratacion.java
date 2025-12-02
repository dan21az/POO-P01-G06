package com.mycompany.app.estudiantil.modelo.Hidratacionn;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class Hidratacion {
    
    private int metaDiaria;
    private List<RegistroHidratacion> registros;
    //Constructor que inicializa la meta diaria y la lista de registros
    public Hidratacion() {
        this.metaDiaria = 2500;
        this.registros = new ArrayList<>();
        //registros.add(new RegistroHidratacion(
            //LocalDate.of(2025, 10, 23),   // fecha 23
            //LocalTime.of(9, 30),          // 09:30 AM
            //400                            // cantidad
    //));Se comento este codigo que agrega registros de hidratacion por defecto pero al ser 
    //de diferentes dias no se mostraban en el historial del dia actual

    ///registros.add(new RegistroHidratacion(
           // LocalDate.of(2025, 10, 24),   // fecha 24
           // LocalTime.of(14, 45),         // 02:45 PM
            //500                            // cantidad
    //));Se comento este codigo que agrega registros de hidratacion por defecto pero al ser 
    //de diferentes dias no se mostraban en el historial del dia actual
    }
    //Registra una ingesta de agua
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
        return registros;
    }
}
