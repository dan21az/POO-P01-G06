package com.mycompany.app.estudiantil.modelo.actividad;

import java.util.ArrayList;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;

public class Academica extends Actividad{

    private ArrayList<SesionEnfoque> sesiones;
    private String asignatura;
    
    public Academica(String n,String c ,String fV, String p, String t, int tE, int pr, int id, String des, String asig){
        super(n, c, fV, p, t, tE, pr, id, des);
        this.asignatura = asig;
        this.sesiones = new ArrayList<>();
    }

    public ArrayList<SesionEnfoque> getSesiones(){
        return sesiones;
    }

    public String getAsignatura(){
        return asignatura;
    }

    public void añadirSesion(SesionEnfoque sesion){
        sesiones.add(sesion);
    }
}
