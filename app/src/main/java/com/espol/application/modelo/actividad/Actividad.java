package com.espol.application.modelo.actividad;

import java.io.Serializable;

public class Actividad implements Serializable {
    private String nombre, categoria, fechaVencimiento, prioridad, tipo, estado, descripcion;
    private int progreso,id;
    private float tiempoEstimado;
    private static int contadorId;

    // --- Constructor ---
    public Actividad(String n, String c, String fV, String p, String t, float tE, int pr, int id, String est,String des) {
        this.nombre = n;
        this.categoria = c;
        this.fechaVencimiento = fV;
        this.prioridad = p;
        this.tipo = t;
        this.tiempoEstimado = tE;
        this.progreso = pr;
        this.id = id;
        this.estado = est;
        this.descripcion = des;
    }

    // --- Métodos Setter (Mutadores) ---

    public static void setContadorId(int nuevoMaxId) {
        if (nuevoMaxId > Actividad.contadorId) {
            Actividad.contadorId = nuevoMaxId;
        }
    }

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
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setTiempoEstimado(float tiempoEstimado) {
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
    public static int getContadorId() {
        return contadorId;
    }

    public String getTipo() {
        return tipo;
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

    public float getTiempoEstimado() {
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

    public String getDescripcion() {
        return descripcion;
    }

// --- Métodos de Lógica ---

    public void actualizarAvance(int i){
        if(progreso<=100  && progreso >= 0 ){
            progreso = i;
            estado = "En curso";
            if(progreso ==100){
                estado = "Completo";
            } else if(progreso == 0){
                estado = "No iniciado";
            }
        };
    }

    public static int aumentarId(){
        return ++contadorId;
    }
}
