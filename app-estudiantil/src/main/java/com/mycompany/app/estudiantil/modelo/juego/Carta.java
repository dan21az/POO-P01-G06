package com.mycompany.app.estudiantil.modelo.juego;
public class Carta {
    private String contenido;
    private boolean descubierta;
    private boolean emparejada;

    public Carta(String contenido) {
        this.contenido = contenido;
        this.descubierta = false;
        this.emparejada = false;
    }

    public String getContenido(){
        return contenido;
    }
    public boolean estaDescubierta(){
        return descubierta;
    }
    public boolean estaEmparejada(){
        return emparejada;
    }

    public void descubrir(){ 
        this.descubierta = true; 
    }
    public void ocultar(){
        this.descubierta = false;
    }
    public void emparejar(){
        this.emparejada = true;
    }
}
