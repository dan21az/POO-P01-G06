package com.mycompany.app.estudiantil.modelo.sesionenfoque;

import com.mycompany.app.estudiantil.modelo.actividad.*;

public class DeepWork extends SesionEnfoque{

    public DeepWork(String fecha, Actividad actividad){
        super(90,5,"DeepWork",fecha, actividad);
    }

    public String toString(){
        return "DeepWork:" + getActividad().getNombre() + "Duracion: " + getDuracionEnfoque();
    }
}
