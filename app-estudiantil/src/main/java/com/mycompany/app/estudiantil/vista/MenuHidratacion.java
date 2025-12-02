package com.mycompany.app.estudiantil.vista;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.Hidratacionn.RegistroHidratacion;

public class MenuHidratacion {


    private Scanner sc = new Scanner(System.in);
    //Se muestra el menu principal de hidratacion
    public int mostrarMenu() {
        System.out.println("\n--- CONTROL DE HIDRATACIÓN ---");
        System.out.println("1. Registrar Ingesta de Agua");
        System.out.println("2. Establecer Meta Diaria");
        System.out.println("3. Ver Progreso Diario y Meta");
        System.out.println("4. Volver al Menú Principal");
        System.out.print("Ingrese su opción: ");
        return sc.nextInt();
    }
    //Lee la cantidad de agua ingerida
    public int leerCantidad() {
        System.out.print("Ingrese la cantidad de agua (ml): ");
        return sc.nextInt();
    }
    //Lee la nueva meta diaria
    public int leerMeta(int metaActual) {
        System.out.println("\n--- ESTABLECER META DIARIA DE HIDRATACIÓN ---");
        System.out.println("Meta diaria actual: " +metaActual + " ml");
        System.out.print("Ingrese la nueva meta diaria (ml): ");
        return sc.nextInt();
    }
    //Confirma la nueva meta diaria
    public boolean confirmarMeta(int nuevaMeta) {
        System.out.print("¿Confirma que la nueva meta diaria es " + nuevaMeta + " ml? (S/N): ");
        String respuesta = sc.next().trim().toUpperCase();
        return respuesta.equals("S");
    }
    //Muestra el registro añadido
    public void mostrarRegistroAñadido(int cantidad) {
        System.out.println("\nRegistro de " + cantidad + " ml añadido.");
    }
    //Muestra el progreso diario y la meta
    public void mostrarProgreso(int total, int meta) {
        double porcentaje = (total * 100.0) / meta;
        int barras = (int) (porcentaje / 10);
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaActual = LocalDate.now().format(formatoFecha);
        System.out.println("\n--- PROGRESO DE HIDRATACIÓN DIARIA ---");
        System.out.println("Fecha: " + fechaActual); 
        System.out.println("Meta Diaria (ml): " + meta + " ml");
        System.out.println("Ingesta Acumulada: " + total + " ml");
        System.out.println("Falta: " + (meta - total) + " ml");
        System.out.print("Progreso: [");
        for (int i = 0; i < 10; i++) {
            System.out.print(i < barras ? "=" : "-");
        }
        System.out.println("] " + String.format("%.1f", porcentaje) + "%" + "(" + total + "/" + meta + ")");
    }
    //Muestra el progreso actualizado despues de cambiar la meta
    public void mostrarProgresoActualizado(int total, int meta) {
        double porcentaje = (total * 100.0) / meta;
        int barras = (int) (porcentaje / 10); 

        System.out.println("\n--- PROGRESO ACTUALIZADO ---");
        System.out.println("Acumulado Hoy: " + total + " ml");
        System.out.println("Nueva Meta: " + meta + " ml");

        System.out.print("Progreso: [");
        for (int i = 0; i < 20; i++) {
        System.out.print(i < barras ? "=" : "-");
        }
    System.out.println("] " + String.format("%.1f", porcentaje) + "% (" + total + "/" + meta + ")");

}
    //Muestra el progreso rapido despues de registrar una ingesta
    public void mostrarProgresoRapido(int total, int meta) {
        double porcentaje = (total * 100.0) / meta;
        int barras = (int) (porcentaje / 10);
        System.out.println("\n--- PROGRESO RAPIDO ---");
        System.out.println("Meta diaria: " + meta + " ml");
        System.out.println("Acumulado hoy: " + total + " ml (Antes eran " + (meta - total) + " ml)" );
        System.out.print("Progreso: [");
        for (int i = 0; i < 10; i++) {
            System.out.print(i < barras ? "=" : "-");
        }
        System.out.println("] " + String.format("%.1f", porcentaje) + "%" + "(" + total + "/" + meta + ")");
    }


    //Mostrara el historial de registros 
    public void mostrarHistorial(List<RegistroHidratacion> lista) {
        System.out.println("\n Historial de registros de hoy:");
        if(lista.isEmpty()) {
            System.out.println("No hay registros de hidratación.");
            
        }else{ 
            
             System.out.println("  Fecha \tCantidad (ml)");
             
             DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm a");
             
            for (RegistroHidratacion r : lista) {
                 
                 String horaFormateada = r.getHora().format(formatoHora);
                 System.out.println("- " +  horaFormateada + "\t" + r.getCantidadAgua() + "ml");
        }
                System.out.println("\n ¡Recuerda mantenerte hidratado! ");
            }
        }
    
    public void esperarEnter() {
        System.out.println("Presione [Enter] para continuar...");
        sc.nextLine();
        sc.nextLine();
        }
    }

