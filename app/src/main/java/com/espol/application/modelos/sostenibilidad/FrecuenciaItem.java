package com.espol.application.modelos.sostenibilidad;

public class FrecuenciaItem {
    public final String accion;
    public final int veces;
    public final int total;
    public final String logro;
    public final int color;

    public FrecuenciaItem(String accion, int veces, int total, String logro, int color) {
        this.accion = accion;
        this.veces = veces;
        this.total = total;
        this.logro = logro;
        this.color = color;
    }
}


