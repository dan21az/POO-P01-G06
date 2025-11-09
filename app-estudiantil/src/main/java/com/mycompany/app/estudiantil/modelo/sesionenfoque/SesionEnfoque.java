package com.mycompany.app.estudiantil.modelo.sesionenfoque;

import com.mycompany.app.estudiantil.modelo.actividad.*;

public class SesionEnfoque {
    private int duracionEnfoque;
    private int duracionDescanso;
    private String tipoTecnica;
    private int contadorTiempo;
    private String fecha;
    private Actividad actividad;
    
    public SesionEnfoque(int duracionEnfoque, int duracionDescanso, String tipoTecnica, String fecha, Actividad actividad) {
        this.duracionEnfoque = duracionEnfoque;
        this.duracionDescanso = duracionDescanso;
        this.tipoTecnica = tipoTecnica;
        this.contadorTiempo = 0;
        this.fecha = fecha;
        this.actividad = actividad;
    }

    public int getDuracionEnfoque() {
        return duracionEnfoque;
    }

    public int getDuracionDescanso() {
        return duracionDescanso;
    }

    public String getTipoTecnica() {
        return tipoTecnica;
    }

    public int getContadorTiempo() {
        return contadorTiempo;
    }

    public String getFecha() {
        return fecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void incrementarTiempo(int minutos){
        if (minutos>=0)
        this.contadorTiempo+=minutos;
    }

    public String toString(){
        return fecha +"\t"+ tipoTecnica+"\t"+contadorTiempo;
    }
}
