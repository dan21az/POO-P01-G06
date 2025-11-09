package com.mycompany.app.estudiantil.modelo.actividad;

public class Actividad {
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



    private static int contadorId;


    public Actividad(String n, String c, String fV, String p, String t, int tE, int pr, int id, String des) {
        this.nombre = n;
        this.categoria = c;
        this.fechaVencimiento = fV;
        this.prioridad = p;
        this.tipo = t;
        this.tiempoEstimado = tE;
        this.progreso = pr;
        this.id = id;
        this.estado = "No Iniciado";
        this.descripción = des;
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

    public static void setContadorId(int contadorId) {
        Actividad.contadorId = contadorId;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

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
    
    public void actualizarEstado(){
        if(progreso<100  && progreso !=0 ){
            estado = "En curso";
        };
    }

    public static void aumentarId(){
        contadorId++;
    }

    public static void disminuirId(){
        contadorId--;
    }

}
