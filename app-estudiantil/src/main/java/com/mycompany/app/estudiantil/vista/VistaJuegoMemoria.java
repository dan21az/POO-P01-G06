package com.mycompany.app.estudiantil.vista;

import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.juego.*;

public class VistaJuegoMemoria {
        private Scanner sc = new Scanner(System.in);

    public void mostrarTablero(Tablero tablero) {
        for (int i = 0; i < 16; i++) {
            Carta card = tablero.getCarta(i);

            if (card.estaEmparejada() || card.estaDescubierta())
                System.out.printf("| %-5s ", card.getContenido());
            else
                System.out.printf("| %-5d ", i+1);

            if ((i+1) % 4 == 0) System.out.println("|");
        }
    }

    public int pedirCarta(String mensaje) {
        System.out.print(mensaje);
        return sc.nextInt() - 1;
    }

    public void mostrarMensaje(String m) {
        System.out.println(m);
    }

    public void esperarEnter() {
    System.out.print("Presione ENTER para continuar.");
    sc.nextLine();
    sc.nextLine();
}
}
