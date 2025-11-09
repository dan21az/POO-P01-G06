package com.mycompany.app.estudiantil.modelo.Hidratacionn;
import java.time.LocalDate;
import java.time.LocalTime;


public class RegistroHidratacion {
    private LocalDate fecha;
    private LocalTime hora;
    private int cantidadAgua; 

    public RegistroHidratacion(LocalDate fecha, LocalTime hora, int cantidadAgua) {
        this.fecha = fecha;
        this.hora = hora;
        this.cantidadAgua = cantidadAgua;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    public int getCantidadAgua() {
        return cantidadAgua;
    }
    public void setCantidadAgua(int cantidadAgua) {
        this.cantidadAgua = cantidadAgua;
    }
    @Override
    public String toString() {
        return "- " + hora + ": " + cantidadAgua + " ml";
    }

}
