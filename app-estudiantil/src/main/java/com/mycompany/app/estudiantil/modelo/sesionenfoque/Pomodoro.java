package com.mycompany.app.estudiantil.modelo.sesionenfoque;
import com.mycompany.app.estudiantil.modelo.actividad.*;
public class Pomodoro extends SesionEnfoque{
    private int ciclos;
    private int cicloActual;

    public int getCiclos() {
        return ciclos;
    }

    public int getCicloActual() {
        return cicloActual;
    }

    public Pomodoro(String fecha, Actividad actividad, int ciclos ){
        super(25,5,"Pomodoro",fecha, actividad);
        this.ciclos = ciclos;
        this.cicloActual = 1;
    }

    public void avanzarCiclo(){
        if(cicloActual<ciclos){
            cicloActual++;
        }
    }

}
