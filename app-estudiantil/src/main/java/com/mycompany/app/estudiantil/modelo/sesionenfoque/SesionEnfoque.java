package com.mycompany.app.estudiantil.modelo.sesionenfoque;

import com.mycompany.app.estudiantil.modelo.actividad.*;
/**
 * Clase SesionEnfoque. 
 * Clase base para modelar una sesión de trabajo concentrado utilizando una técnica de productividad.
 * Contiene los atributos comunes y la lógica para registrar el avance en la actividad asociada.
 */
public class SesionEnfoque {
    // --- Atributos ---
    private int duracionEnfoque;
    private int duracionDescanso;
    private String tipoTecnica;
    private int contadorTiempo;
    private String fecha;
    private Actividad actividad;

    /**
     * Constructor de la clase SesionEnfoque.
     * @param duracionEnfoque Duración del enfoque.
     * @param duracionDescanso Duración del descanso.
     * @param tipoTecnica Nombre de la técnica utilizada.
     * @param fecha Fecha de la sesión.
     * @param actividad Actividad a la que se asocia la sesión.
     */
    public SesionEnfoque(int duracionEnfoque, int duracionDescanso, String tipoTecnica, String fecha, Actividad actividad) {
        this.duracionEnfoque = duracionEnfoque;
        this.duracionDescanso = duracionDescanso;
        this.tipoTecnica = tipoTecnica;
        this.contadorTiempo = 0; // Inicializado a 0 por defecto.
        this.fecha = fecha;
        this.actividad = actividad;
    }

    // --- Métodos Getter (Accesores) ---
    public int getDuracionEnfoque() {
        return duracionEnfoque;
    }

    public int getDuracionDescanso() {
        return duracionDescanso;
    }

    public String getTipoTecnica() {
        return tipoTecnica;
    }

    public int getContadorTiempo() {
        return contadorTiempo;
    }

    public String getFecha() {
        return fecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    // --- Métodos Setter (Mutadores) ---
    public void setDuracionEnfoque(int duracionEnfoque) {
        this.duracionEnfoque = duracionEnfoque;
    }

    public void setDuracionDescanso(int duracionDescanso) {
        this.duracionDescanso = duracionDescanso;
    }

    public void setTipoTecnica(String tipoTecnica) {
        this.tipoTecnica = tipoTecnica;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
    public void setContadorTiempo(int i){
        this.contadorTiempo = i;
    }
// --- Métodos de Utilidad ---

    /**
     * Retorna una representación en String de la sesión (para visualización/registro).
     * @return String con Fecha, Técnica y Contador de Tiempo.
     */
    public String toString(){
        return fecha +"\t"+ tipoTecnica+"\t"+contadorTiempo;
    }
/**
     * Registra la finalización de la sesión y actualiza el progreso de la actividad asociada.
     * @param nuevoProgreso Cantidad de porcentaje a añadir al progreso actual de la actividad.
     */
    public void finalizarSesion(int nuevoProgreso){
        // Calcula el nuevo progreso sumando el progreso actual más el avance de esta sesión.
        int progreso = actividad.getProgreso() + nuevoProgreso;
        // Llama al método de la actividad para actualizar su estado y progreso.
        actividad.actualizarAvance(progreso);
    }
}

