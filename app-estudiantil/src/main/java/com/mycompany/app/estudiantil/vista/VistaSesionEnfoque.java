package com.mycompany.app.estudiantil.vista;
import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.sesionenfoque.SesionEnfoque;

public class VistaSesionEnfoque {

    private Scanner sc;

    public VistaSesionEnfoque(){
        this.sc = new Scanner(System.in);

    }

    public int menuSesion(){
        System.out.println("--- T É C N I C A S  D E  E N F O Q U E ---");
        System.out.println("1. Iniciar Pomodoro (25 min Trabajo / 5 min Descanso)");
        System.out.println("2. Iniciar Deep Work (Sesión Larga de 90 min)");
        System.out.println("3. Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void encabezado(String encabezado){
        System.out.println(encabezado);
        System.out.println("Seleccione una actividad a trabajar: ");
    }



    public void mostrarDetalles(SesionEnfoque s){
        System.out.println(s.getFecha()+"\t"+s.getTipoTecnica()+"\t"+s.getDuracionEnfoque());
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje); 
        System.out.print("Presione [ENTER] para continuar...");
        sc.nextLine();
    }

    public void mostrarInicioTrabajo(String nombreActividad, String tipoTecnica, int cicloActual, int ciclosTotales,
            int duracionMinutos) {
        System.out.println("\n>>> INICIANDO TRABAJO EN '" + nombreActividad + "' <<<");
        if (ciclosTotales > 1) {
            System.out.println("Técnica: " + tipoTecnica + " | Ciclo: " + cicloActual + "/" + ciclosTotales);
        } else {
            System.out.println("Técnica: " + tipoTecnica);
        }
        System.out.println("Tiempo de Trabajo: " + duracionMinutos + ":00 minutos restantes");
    }

    // 2. Muestra la pausa y pide el ENTER para continuar/finalizar
    public void solicitarFinalizacionTrabajo(int duracionMinutos, String tipoTecnica) {
        String accion = (tipoTecnica.equals("Pomodoro")) ? "finalizar el tiempo de TRABAJO" : "finalizar la SESIÓN";
        System.out.print("[Simulación: Presione [ENTER] para " + accion + "...]");
        sc.nextLine();
    }

    // 3. Muestra el fin del trabajo y el inicio del descanso (si aplica)
    public void mostrarFinTrabajoYDescanso(String tipoTecnica, int duracionDescansoMinutos) {
        System.out.println("\n--- ¡TIEMPO DE TRABAJO TERMINADO! ---");
        System.out.println("Sesión registrada. (Avance de la actividad actualizado en base al tiempo).");
        if (tipoTecnica.equals("Pomodoro")) {
            System.out.println("Ahora toma un DESCANSO: " + duracionDescansoMinutos + ":00 minutos restantes");
            System.out.println("Tiempo de Descanso: " + duracionDescansoMinutos + ":00 minutos restantes");
            System.out.print("[Simulación: Presione [ENTER] para finalizar el tiempo de DESCANSO...]");
        sc.nextLine();
        }
    }
}

