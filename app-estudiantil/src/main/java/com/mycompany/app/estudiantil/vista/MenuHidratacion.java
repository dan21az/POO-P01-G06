package com.mycompany.app.estudiantil.vista;
import java.util.Scanner;

public class MenuHidratacion {


    private Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n--- CONTROL DE HIDRATACIÓN ---");
        System.out.println("1. Registrar Ingesta de Agua");
        System.out.println("2. Establecer Meta Diaria");
        System.out.println("3. Ver Progreso Diario y Meta");
        System.out.println("4. Volver al Menú Principal");
        System.out.print("Ingrese su opción: ");
        return sc.nextInt();
    }

    public int leerCantidad() {
        System.out.print("Ingrese la cantidad de agua (ml): ");
        return sc.nextInt();
    }

    public int leerMeta() {
        System.out.print("Ingrese la nueva meta diaria (ml): ");
        return sc.nextInt();
    }

    public void mostrarProgreso(int total, int meta) {
        double porcentaje = (total * 100.0) / meta;
        int barras = (int) (porcentaje / 10);

        System.out.println("\n--- PROGRESO DE HIDRATACIÓN DIARIA ---");
        System.out.println("Meta: " + meta + " ml");
        System.out.println("Acumulado: " + total + " ml");
        System.out.println("Falta: " + (meta - total) + " ml");
        System.out.print("Progreso: [");
        for (int i = 0; i < 10; i++) {
            System.out.print(i < barras ? "=" : "-");
        }
        System.out.println("] " + String.format("%.1f", porcentaje) + "%");
    }
    public void esperarEnter() {
        System.out.println("Presione[Enter] para continuar...");
        sc.nextLine();
        sc.nextLine();
        }
}

