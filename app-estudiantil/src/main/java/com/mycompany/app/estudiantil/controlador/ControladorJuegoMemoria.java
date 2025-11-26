package com.mycompany.app.estudiantil.controlador;

import com.mycompany.app.estudiantil.modelo.juego.*;
import com.mycompany.app.estudiantil.vista.VistaJuegoMemoria;

public class ControladorJuegoMemoria {

        Juego juego = new Juego();
        VistaJuegoMemoria vista = new VistaJuegoMemoria();
    
        public void iniciarJuego(){

        vista.mostrarMensaje("=== JUEGO DE MEMORIA ECOLÓGICO ===");

        int turno = 1;

        while (!juego.juegoTerminado()) {

            System.out.println("\n--- TURNO "+ turno + " | PARES ENCONTRADOS: " + juego.getParesEncontrados() + "/8");
            vista.mostrarTablero(juego.getTablero());

            // PRIMERA CARTA
            int c1 = vista.pedirCarta("Número de la primera carta: ");
            while (c1 < 0 || c1 > 15) {
                vista.mostrarMensaje("Número no válido, debe ser entre 1 y 16.");
                c1 = vista.pedirCarta("Número de la primera carta: ");
            }

            // SEGUNDA CARTA
            int c2 = vista.pedirCarta("Número de la segunda carta: ");
            while (c2 < 0 || c2 > 15 || c1 == c2) {
                vista.mostrarMensaje("Número no válido o no puede repetir la carta.");
                c2 = vista.pedirCarta("Número de la segunda carta: ");
            }

            // REVELAR
            Carta carta1 = juego.getTablero().getCarta(c1);
            Carta carta2 = juego.getTablero().getCarta(c2);

            carta1.descubrir();
            carta2.descubrir();

            vista.mostrarTablero(juego.getTablero());

            // VERIFICAR
            boolean acierto = juego.seleccionarCartas(c1, c2);

            if (acierto) {
                vista.mostrarMensaje("¡PAR ENCONTRADO!");
            } else {
                vista.mostrarMensaje("No coinciden. Se ocultan...");
                carta1.ocultar();
                carta2.ocultar();
            }
            // PAUSA PARA ENTER
            vista.esperarEnter(); 
            turno++;
        }

        vista.mostrarMensaje("¡Juego completado!");
        vista.mostrarMensaje("Intentos totales: " + juego.getIntentos());
    }
}
