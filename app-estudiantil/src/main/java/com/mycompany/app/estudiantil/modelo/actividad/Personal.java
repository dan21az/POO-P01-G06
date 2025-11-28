package com.mycompany.app.estudiantil.modelo.actividad;
/**
 * Clase Personal. 
 * Extiende la clase base Actividad y representa una tarea o evento
 * específicamente relacionado con la vida personal (Ej: Citas, Ejercicio, Hobbies).
 * Añade un atributo específico para el lugar donde se realiza la actividad.
 */
public class Personal extends Actividad{
    // Atributo específico: el lugar donde se llevará a cabo la actividad personal.
    private String lugar;

    /**
     * Constructor de la clase Personal.
     * Llama al constructor de la clase padre (Actividad) e inicializa el atributo específico 'lugar'.
     * @param n Nombre de la actividad.
     * @param c Categoría (debería ser "PERSONAL").
     * @param fV Fecha de vencimiento.
     * @param p Prioridad.
     * @param t Tipo de actividad (Ej: "CITAS", "EJERCICIO").
     * @param tE Tiempo estimado.
     * @param pr Progreso inicial.
     * @param id ID de la actividad.
     * @param est Estado inicial.
     * @param des Descripción.
     * @param l Lugar donde se realizará la actividad.
     */
    public Personal(String n, String c, String fV, String p, String t, int tE, int pr, int id,String est, String des, String l){
        // Llama al constructor de la superclase Actividad.
        super(n, c, fV, p, t, tE, pr, id, est ,des);
        this.lugar = l;
    }

    // --- Métodos Getter (Accesor) ---

    /**
     * Obtiene el lugar asociado a la actividad personal.
     * @return El lugar de la actividad.
     */
    public String getLugar() {
        return lugar;
    }

    // --- Métodos Setter (Mutador) ---

    /**
     * Establece o actualiza el lugar asociado a la actividad personal.
     * @param lugar El nuevo lugar de la actividad.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
