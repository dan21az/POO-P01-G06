package com.mycompany.app.estudiantil.vista;

import java.util.*;

/**
 * Clase VistaMenuPrincipal.
 * Responsable de mostrar el menú principal de la aplicación FOCUS APP
 * y capturar la opción de navegación seleccionada por el usuario.
 */
public class VistaMenuPrincipal {

    // Objeto Scanner para capturar la entrada del usuario por consola.
    private Scanner sc;

    /**
     * Constructor. Inicializa el objeto Scanner.
     */
    public VistaMenuPrincipal(){
        this.sc = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal con todas las funcionalidades de la aplicación.
     * @return La opción numérica seleccionada por el usuario, o -1 si hay un error de formato.
     */
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
        // Intenta leer la línea y convertirla a entero.
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
        // Si el usuario ingresa texto o un formato inválido.
            return -1;
        }
    }

    /**
     * Muestra un mensaje de error cuando la opción ingresada no es válida.
     */
    public void noValido(){
        System.out.println("Opcion no valida!");
        System.out.println("Presiona [Enter] para volver");
        sc.nextLine();
    }

    /**
     * Muestra el mensaje de despedida al salir de la aplicación.
     */
    public void salir(){
        System.out.println("Saliendo...");
    }

    /**
     * Muestra un mensaje indicando que una funcionalidad aún no está implementada.
     */
    public void noDisponible(){
        System.out.println("Esta opción aún no está disponible");
        System.out.println("Presiona [Enter] para volver");
        sc.nextLine();
    }

}
