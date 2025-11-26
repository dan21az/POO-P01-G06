package com.mycompany.app.estudiantil.vista;

import java.util.*;

public class VistaMenuPrincipal {

    private Scanner sc;

    public VistaMenuPrincipal(){
        this.sc = new Scanner(System.in);
    }

    public int menuPrincipal(){
        System.out.println("--- F O C U S  A P P ---");
        System.out.println("1. Gestión de Actividades");
        System.out.println("2. Técnicas de Enfoque (Manejo de tiempo)");
        System.out.println("3. Control de hidratación");
        System.out.println("4. Registro diario de Sostenibilidad");
        System.out.println("5. Juego de memoria");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void noValido(){
        System.out.println("Opcion no valida!");
        System.out.println("Presiona [Enter] para volver");
        sc.nextLine();
    }

    public void salir(){
        System.out.println("Saliendo...");
    }

    public void noDisponible(){
        System.out.println("Esta opción aún no está disponible");
        System.out.println("Presiona [Enter] para volver");
        sc.nextLine();
    }

}
