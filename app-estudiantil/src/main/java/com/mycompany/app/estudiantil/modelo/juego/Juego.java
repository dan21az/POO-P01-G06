package com.mycompany.app.estudiantil.modelo.juego;

public class Juego {
    private Tablero tablero;
    private int intentos;
    private int paresEncontrados;

    public Juego() {
        tablero = new Tablero();
        intentos = 0;
        paresEncontrados = 0;
    }

    //verifica coincidencia y empareja
    public boolean seleccionarCartas(int c1, int c2) {
        intentos++;

        Carta carta1 = tablero.getCarta(c1);
        Carta carta2 = tablero.getCarta(c2);

        if (carta1.getContenido().equals(carta2.getContenido())) {
            carta1.emparejar();
            carta2.emparejar();
            paresEncontrados++;
            return true;
        } else {
            return false;
        }
    }

    public boolean juegoTerminado() {
        return paresEncontrados == 8;
    }

    public Tablero getTablero(){
        return tablero;
    }
    
    public int getIntentos(){
        return intentos;
    }

    public int getParesEncontrados(){
        return paresEncontrados;
    }
}
