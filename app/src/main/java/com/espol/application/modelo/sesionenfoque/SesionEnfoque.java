package com.espol.application.modelo.sesionenfoque;

import com.espol.application.modelo.actividad.Actividad;

import java.io.Serializable;

public class SesionEnfoque implements Serializable {

    private long duracionEnfoqueMs;
    private int duracionDescanso;
    private String tipoTecnica,fecha;
    private Actividad actividad;

    // --- Constructores ---
    public SesionEnfoque(long duracionMs, int duracionDescansoMin, String tipo, String fecha, Actividad actividad) {
        this.duracionEnfoqueMs = duracionMs;
        this.duracionDescanso = duracionDescansoMin;
        this.tipoTecnica = tipo;
        this.fecha = fecha;
        this.actividad = actividad;
    }

    // --- Getters ---
    public long getDuracionEnfoqueMs() {
        return duracionEnfoqueMs;
    }

    public int getDuracionDescanso() {
        return duracionDescanso;
    }
    public String getTipoTecnica() {
        return tipoTecnica;
    }

    public String getFecha() {
        return fecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    // --- Setters ---
    public void setDuracionEnfoqueMs(long duracionEnfoqueMs) {
        this.duracionEnfoqueMs = duracionEnfoqueMs;
    }

}

