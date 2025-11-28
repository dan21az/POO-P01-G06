package com.mycompany.app.estudiantil.modelo.juego;
import java.util.Collections;
import java.util.ArrayList;

public class Tablero {
    private Carta[] cartas;

    public Tablero() {
        String[] elementos = {
            "RECICLAR", "REDUCIR", "AGUA", "VERDE",
            "FAUNA", "REUSAR", "BASURA", "OCEANO"
        };

        ArrayList<String> pares = new ArrayList<>();
        for (String elemento : elementos) {
            pares.add(elemento);
            pares.add(elemento);
        }

        Collections.shuffle(pares);

        cartas = new Carta[16];
        for (int i = 0; i < 16; i++) {
            cartas[i] = new Carta(pares.get(i));
        }
    }

    public Carta getCarta(int indice) {
        return cartas[indice];
    }
    public void mostrarPares(){
        System.out.println("\n--- PARES DEL TABLERO ---");
        for (int i = 0; i < 16; i++){
            for (int j = i + 1; j < 16; j++){
                if (cartas[i].getContenido().equals(cartas[j].getContenido())) {
                    System.out.println((i + 1) + " - " + (j + 1) + " - " + cartas[i].getContenido());
                }
            }
        }
    }
}

