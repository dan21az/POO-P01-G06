package com.mycompany.app.estudiantil.vista;

public class VistaMenuPrincipal {

    public void menuPrincipal(){
        System.out.println("--- F O C U S  A P P ---");
        System.out.println("1. Gestión de Actividades");
        System.out.println("2. Técnicas de Enfoque (Manejo de tiempo)");
        System.out.println("3. Control de hidratación");
        System.out.println("4. Registro diario de Sostenibilidad");
        System.out.println("5. Juego de memoria");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void noValido(){
        System.out.println("Opcion no valida!");
    }

    public void salir(){
        System.out.println("Saliendo...");
    }


}
