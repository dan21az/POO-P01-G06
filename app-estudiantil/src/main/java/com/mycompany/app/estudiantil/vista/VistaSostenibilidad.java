package com.mycompany.app.estudiantil.vista;
import com.mycompany.app.estudiantil.modelo.sostenibilidad.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaSostenibilidad {
    private Scanner sc = new Scanner(System.in);

    public int mostrarMenuSostenibilidad() {
        System.out.println("=======================================");
        System.out.println("     MENÚ DE SOSTENIBILIDAD ");
        System.out.println("=======================================");
        System.out.println("1. Registrar acciones sostenibles del día");
        System.out.println("2. Volver al menú principal");

        System.out.print("Seleccione una opción: ");
        //Algoritmo para evitar error de compilacion si el usuario ingresa un dato no entero
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
<<<<<<< HEAD
    //menu de Registrar Accion
=======

>>>>>>> d719903cf6984712fbe696a37151fc3c15aad508
    public ArrayList<Integer> mostrarSubmenuAcciones() {
        System.out.println("\n--- REGISTRO DIARIO DE SOSTENIBILIDAD (" + java.time.LocalDate.now() + ") ---");
        System.out.println("Marque las acciones que realizó hoy:");
        System.out.println("1. Usé transporte público, bicicleta o caminé.");
        System.out.println("2. No realicé impresiones.");
        System.out.println("3. No utilicé envases descartables (usé mi termo/taza).");
        System.out.println("4. Separé y reciclé materiales (vidrio, plástico, papel).");
        System.out.print("Seleccione una opción: ");
        String entrada = sc.nextLine();
        String[] partes = entrada.split(",");
        ArrayList<Integer> opciones = new ArrayList<>();
        for (String p : partes) {
            try{
                opciones.add(Integer.parseInt(p.trim()));
            }catch (NumberFormatException e) {
                System.out.println("Valor inválido ignorado: " + p);
            }
        }
        return opciones;
        
        
    }
    //Metodo para mostrar todas las acciones registradas al final de interaccion
    public void mostrarConfirmacionRegistro(RegistroSostenible r) {
        System.out.println("\n----------------------------------------");
        System.out.println("Acciones de sostenibilidad registradas:");
        for (String a : r.getAcciones())
            System.out.println("- " + a);
        System.out.println("\n¡Excelente contribución al planeta hoy!");
        System.out.println("Puntos de Sostenibilidad Acumulados: +" + r.getPuntos());
        System.out.println("----------------------------------------");
    }
    //Informe semanal (Muestra la informacion de Sostenibilidad)
    public void mostrarInformeSemanal(ArrayList<RegistroSostenible> registros) {
        LocalDate hoy = LocalDate.now();
        LocalDate lunes = hoy.with(java.time.DayOfWeek.MONDAY);
        LocalDate domingo = hoy.with(java.time.DayOfWeek.SUNDAY);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n--- RESUMEN SEMANAL DE SOSTENIBILIDAD (" + lunes.format(f) + " - " + domingo.format(f) + ") ---");
        if (registros.isEmpty()) {
            System.out.println("No hay registros disponibles.");
            return;
        }

        int tPublico = 0, impresiones = 0, envases = 0, reciclaje = 0;
        for (RegistroSostenible r : registros) {
            for (String a : r.getAcciones()) {
                if (a.contains("transporte")) tPublico++;
                else if (a.contains("impresiones")) impresiones++;
                else if (a.contains("envases")) envases++;
                else if (a.contains("reciclé")) reciclaje++;
            }
        }

        int totalDias = registros.size();
        System.out.println("-------------------------------------------------------------");
        System.out.println("ACCIÓN\t\t\t\t| VECES REALIZADA | LOGRO");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Usé transporte público\t\t| %d / %d Días | %s%n", tPublico, totalDias, (tPublico >= 5 ? "¡Gran Movilidad!" : "Mejorable"));
        System.out.printf("No realicé impresiones\t\t| %d / %d Días | %s%n", impresiones, totalDias, (impresiones == totalDias ? "Excelente" : "Debe mejorar"));
        System.out.printf("No utilicé envases\t\t| %d / %d Días | %s%n", envases, totalDias, (envases >= 4 ? "Muy bien" : "Necesita mejorar"));
        System.out.printf("Separé y reciclé materiales\t| %d / %d Días | %s%n", reciclaje, totalDias, (reciclaje >= 5 ? "Muy bien" : "Regular"));

        System.out.println("-------------------------------------------------------------");
        System.out.println("Días con al menos 1 acción sostenible: " + totalDias + " de " + totalDias + " (100%)");
        System.out.println("Días con las 4 acciones completas: " + contarDiasCompletos(registros) + " de " + totalDias);
        System.out.println("\n**Tip Ecológico de la Semana:** Lleva siempre tu termo o botella reutilizable ");
        System.out.println("-------------------------------------------------------------");
    }
    //metodo para contar los dias en que se cumplieron las 4 acciones 
    private int contarDiasCompletos(ArrayList<RegistroSostenible> registros) {
        int cont = 0;
        for (RegistroSostenible r : registros)
            if (r.getAcciones().size() == 4) cont++;
        return cont;
    }
    //metodos para para añadir la interaccion de enter y usuario
    public void pausa() {
        System.out.print("\nPresione [ENTER] para continuar...");
        sc.nextLine();
    }
    public void pausarParaInforme() {
        System.out.print("\nPresione [ENTER] para ver registro semanal...");
        sc.nextLine();
    }
    public void pausarParaSalir() {
        System.out.print("\nPresione [ENTER] para regresar al menú principal...");
        sc.nextLine();
    }

}