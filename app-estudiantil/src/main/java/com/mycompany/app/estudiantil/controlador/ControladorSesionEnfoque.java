package com.mycompany.app.estudiantil.controlador;

import com.mycompany.app.estudiantil.modelo.actividad.*;
import com.mycompany.app.estudiantil.modelo.actividad.Academica;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;
import com.mycompany.app.estudiantil.vista.MensajeUsuario;

import java.util.ArrayList;

public class ControladorSesionEnfoque {

    private ArrayList<SesionEnfoque> sesionesEnfoque;
    private ControladorActividad ca;

    public ArrayList<SesionEnfoque> getSesionesEnfoque() {
        return sesionesEnfoque;
    }

    public ControladorSesionEnfoque(ControladorActividad ca) {
        this.ca = ca;
        this.sesionesEnfoque = new ArrayList<>();
    }

    public MensajeUsuario iniciarPomodoro(String fecha, Actividad actividad, int ciclos ){
        Pomodoro p = new Pomodoro(fecha, actividad, ciclos);
        sesionesEnfoque.add(p);
        return new MensajeUsuario("Pomodoro", "Iniciado con éxito");
    }

    public MensajeUsuario iniciarDeepWork(String fecha, Actividad actividad){
        DeepWork d = new DeepWork(fecha, actividad);
        sesionesEnfoque.add(d);
        return new MensajeUsuario("DeepWork ", "Iniciado con éxito");
    }

    public void finalizarSesion(SesionEnfoque s){
        guardarProgreso(s);
        Academica ac = (Academica) s.getActividad();
        ac.añadirSesion(s);
    }

    public void guardarProgreso(SesionEnfoque s){
        int t = 100;
        ca.cambiarProgreso(s.getActividad().getId(),t);
        s.getActividad().actualizarEstado();
    
    }
    
}

