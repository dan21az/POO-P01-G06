package com.espol.application.modelo.actividad;

import com.espol.application.modelo.sesionenfoque.SesionEnfoque;

import java.io.Serializable;
import java.util.ArrayList;

public class Academica extends Actividad implements Serializable {

    private ArrayList<SesionEnfoque> sesiones; // Lista para almacenar las sesiones de enfoque realizadas para esta actividad.
    private String asignatura;

    // --- Constructor ---
    public Academica(String n,String c ,String fV, String p, String t, float tE, int pr, int id, String est, String des, String asig){
        super(n, c, fV, p, t, tE, pr, id, est ,des);
        this.asignatura = asig;
        this.sesiones = new ArrayList<>();
    }

    // --- Métodos Getter (Accesores) ---
    public ArrayList<SesionEnfoque> getSesiones(){
        return sesiones;
    }

    public String getAsignatura(){
        return asignatura;
    }

    // --- Métodos Setter (Mutadores) ---
    public void setSesiones(ArrayList<SesionEnfoque> sesiones) {
        this.sesiones = sesiones;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    // --- Métodos de Lógica ---
    public void añadirSesion(SesionEnfoque sesion){
        sesiones.add(sesion);
    }
}
