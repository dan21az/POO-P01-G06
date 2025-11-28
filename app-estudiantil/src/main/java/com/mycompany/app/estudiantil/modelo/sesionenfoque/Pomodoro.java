package com.mycompany.app.estudiantil.modelo.sesionenfoque;
import com.mycompany.app.estudiantil.modelo.actividad.*;
/**
 * Clase Pomodoro. 
 * Extiende SesionEnfoque para implementar la técnica Pomodoro.
 * Esta técnica utiliza ciclos fijos (típicamente 25 min de enfoque, 5 min de descanso)
 * y requiere gestionar el número de ciclos completados. 
 */
public class Pomodoro extends SesionEnfoque{
    // --- Atributos Específicos de Pomodoro ---
    private int ciclos;
    private int cicloActual;

    /**
     * Constructor de la clase Pomodoro.
     * Llama al constructor de la superclase (SesionEnfoque) con los valores fijos de Pomodoro
     * (25 minutos de enfoque, 5 minutos de descanso, nombre "Pomodoro").
     * @param fecha Fecha de la sesión.
     * @param actividad Actividad asociada.
     * @param ciclos Número de ciclos planificados.
     */
    public Pomodoro(String fecha, Actividad actividad, int ciclos ){
        // Valores por defecto de Pomodoro: 25 min enfoque, 5 min descanso, Tipo "Pomodoro".
        super(25,5,"Pomodoro",fecha, actividad);
        this.ciclos = ciclos;
        this.cicloActual = 1; // La sesión siempre comienza en el primer ciclo.
    }

    // --- Métodos Getter (Accesores) ---
    public int getCiclos() {
        return ciclos;
    }

    public int getCicloActual() {
        return cicloActual;
    }

    // --- Métodos Setter (Mutadores) ---
    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    public void setCicloActual(int cicloActual) {
        this.cicloActual = cicloActual;
    }

    // --- Lógica Específica ---

    /**
     * Incrementa el contador del ciclo actual, siempre y cuando no se haya superado
     * el número total de ciclos planificados.
     */
    public void avanzarCiclo(){
        if(cicloActual<ciclos){
            cicloActual++;
        }
    }

}
