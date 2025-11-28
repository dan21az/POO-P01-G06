package com.mycompany.app.estudiantil.modelo.actividad;

import java.util.ArrayList;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;

/**
 * Clase Academica. 
 * Extiende la clase base Actividad y representa una tarea o evento
 * específicamente relacionado con los estudios (Ej: Tarea, Examen, Proyecto).
 * Gestiona una lista de sesiones de enfoque asociadas a esta tarea.
 */
public class Academica extends Actividad{

    // Lista para almacenar las sesiones de enfoque realizadas para esta actividad.
    private ArrayList<SesionEnfoque> sesiones;
    private String asignatura;

    /**
     * Constructor de la clase Academica.
     * Llama al constructor de la clase padre (Actividad) e inicializa los atributos específicos.
     * * @param n Nombre de la actividad.
     * @param c Categoría (debería ser "ACADEMICA").
     * @param fV Fecha de vencimiento.
     * @param p Prioridad.
     * @param t Tipo de actividad (Ej: "TAREA", "EXAMEN").
     * @param tE Tiempo estimado.
     * @param pr Progreso inicial.
     * @param id ID de la actividad.
     * @param est Estado inicial.
     * @param des Descripción.
     * @param asig Asignatura a la que pertenece la actividad.
     */
    public Academica(String n,String c ,String fV, String p, String t, int tE, int pr, int id, String est, String des, String asig){
        // Llama al constructor de la clase Actividad (superclase).
        super(n, c, fV, p, t, tE, pr, id, est ,des);
        this.asignatura = asig;
        // Inicializa la lista de sesiones de enfoque.
        this.sesiones = new ArrayList<>();
    }

// --- Métodos Getter (Accesores) ---

    /**
     * Obtiene la lista de sesiones de enfoque asociadas a esta actividad.
     * @return La lista de objetos SesionEnfoque.
     */
    public ArrayList<SesionEnfoque> getSesiones(){
        return sesiones;
    }
    
    /**
     * Obtiene el nombre de la asignatura asociada.
     * @return El nombre de la asignatura.
     */
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

    /**
     * Añade una nueva sesión de enfoque (Pomodoro o DeepWork) a la lista.
     * @param sesion El objeto SesionEnfoque a añadir.
     */
    public void añadirSesion(SesionEnfoque sesion){
        sesiones.add(sesion);
    }
}
