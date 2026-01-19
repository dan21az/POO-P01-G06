package com.espol.application.modelos.actividad;

import java.io.Serializable;

public class Personal extends Actividad implements Serializable {
    private String lugar;

    // --- Constructor ---
    public Personal(String n, String c, String fV, String p, String t, float tE, int pr, int id,String est, String des, String l){
        super(n, c, fV, p, t, tE, pr, id, est ,des);
        this.lugar = l;
    }

    // --- Métodos Getter (Accesor) ---
    public String getLugar() {
        return lugar;
    }

    // --- Métodos Setter (Mutador) ---
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
