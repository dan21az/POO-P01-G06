package com.mycompany.app.estudiantil.modelo.actividad;

/**
 * Clase base Actividad. 
 * Representa una actividad genérica (padre) que puede ser Académica o Personal.
 * Contiene los atributos comunes a todas las actividades y la lógica para el control de ID y progreso.
 */
public class Actividad {
    // --- Atributos de Instancia (Características de cada actividad) ---
    private String nombre;
    private String categoria;
    private String fechaVencimiento;
    private String prioridad;
    private String tipo;
    private int tiempoEstimado;
    private int progreso;
    private int id;
    private String estado;
    private String descripción;

    // Contador estático para asignar IDs únicos automáticamente.    
    private static int contadorId;

/**
     * Constructor de la clase Actividad.
     * @param n Nombre de la actividad.
     * @param c Categoría (ACADEMICA/PERSONAL).
     * @param fV Fecha de vencimiento.
     * @param p Prioridad.
     * @param t Tipo de actividad.
     * @param tE Tiempo estimado.
     * @param pr Progreso inicial.
     * @param id ID asignado.
     * @param est Estado inicial.
     * @param des Descripción.
     */
    public Actividad(String n, String c, String fV, String p, String t, int tE, int pr, int id, String est,String des) {
        this.nombre = n;
        this.categoria = c;
        this.fechaVencimiento = fV;
        this.prioridad = p;
        this.tipo = t;
        this.tiempoEstimado = tE;
        this.progreso = pr;
        this.id = id;
        this.estado = est;
        this.descripción = des;
    }

    // --- Métodos Setter (Mutadores) ---

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // --- Métodos Getter (Accesores) ---

    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene el valor actual del contador de IDs.
     * @return El último ID asignado.
     */
    public static int getContadorId() {
        return contadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public int getProgreso() {
        return progreso;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripción() {
        return descripción;
    }

// --- Métodos de Lógica ---
    
    /**
     * Actualiza el progreso de la actividad y ajusta el estado en consecuencia.
     * @param i El nuevo valor del progreso (de 0 a 100).
     */
    public void actualizarAvance(int i){
        // Validar que el progreso se encuentre dentro del rango válido [0, 100].
        if(progreso<=100  && progreso >= 0 ){
            progreso = i;
            estado = "En curso";
            // Lógica para actualizar el estado automáticamente
            if(progreso ==100){
            estado = "Completo";
            } else if(progreso == 0){
            estado = "No iniciado";
            }
        };
    }

    /**
     * Incrementa el contador estático para generar el ID de la siguiente actividad.
     */
    public static void aumentarId(){
        contadorId++;
    }
}
