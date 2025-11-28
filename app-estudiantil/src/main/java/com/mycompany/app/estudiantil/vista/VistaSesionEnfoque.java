package com.mycompany.app.estudiantil.vista;
import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.sesionenfoque.SesionEnfoque;

/**
 * Clase VistaSesionEnfoque. 
 * Responsable de la interacción con el usuario para iniciar, simular y finalizar
 * las sesiones de enfoque (Pomodoro y Deep Work).
 */
public class VistaSesionEnfoque {

    // Objeto Scanner para capturar la entrada del usuario por consola.
    private Scanner sc;

    /**
     * Constructor. Inicializa el objeto Scanner.
     */ 
    public VistaSesionEnfoque(){
        this.sc = new Scanner(System.in);

    }

    /**
     * Muestra el menú de selección de técnicas de enfoque.
     * @return La opción numérica seleccionada (1, 2, 3) o -1 si hay error de formato.
     */
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

    /**
     * Muestra un encabezado para la selección de actividades antes de iniciar la sesión.
     * @param encabezado El texto del encabezado (Ej: "--- INICIAR POMODORO ---").
     */
    public void encabezado(String encabezado){
        System.out.println(encabezado);
        System.out.println("Seleccione una actividad a trabajar: ");
    }

    /**
     * Muestra los detalles de una sesión de enfoque (utilidad básica).
     * @param s La sesión de enfoque.
     */
    public void mostrarDetalles(SesionEnfoque s){
        System.out.println(s.getFecha()+"\t"+s.getTipoTecnica()+"\t"+s.getDuracionEnfoque());
    }

    /**
     * Muestra un mensaje al usuario y pausa la ejecución hasta que se presione ENTER.
     * @param mensaje El mensaje a mostrar.
     */ 
    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje); 
        System.out.print("Presione [ENTER] para continuar...");
        sc.nextLine();
    }

    /**
     * Muestra el mensaje de inicio de la fase de trabajo de la sesión.
     * @param nombreActividad Nombre de la tarea a trabajar.
     * @param tipoTecnica Nombre de la técnica (Pomodoro/DeepWork).
     * @param cicloActual Ciclo actual (para Pomodoro).
     * @param ciclosTotales Ciclos totales planificados (para Pomodoro).
     * @param duracionMinutos Duración de la fase de enfoque.
     */
    public void mostrarInicioTrabajo(String nombreActividad, String tipoTecnica, int cicloActual, int ciclosTotales,
            int duracionMinutos) {
        System.out.println("\n>>> INICIANDO TRABAJO EN '" + nombreActividad + "' <<<");
        /// Muestra información de ciclos solo si es Pomodoro o si hay más de 1 ciclo.
        if (ciclosTotales > 1) {
            System.out.println("Técnica: " + tipoTecnica + " | Ciclo: " + cicloActual + "/" + ciclosTotales);
        } else {
            System.out.println("Técnica: " + tipoTecnica);
        }
        System.out.println("Tiempo de Trabajo: " + duracionMinutos + ":00 minutos restantes");
    }

/**
     * Simula la espera del tiempo de trabajo, pidiendo al usuario presionar ENTER.
     * @param duracionMinutos Duración del trabajo (solo para mensaje).
     * @param tipoTecnica Nombre de la técnica para adaptar el mensaje.
     */
    public void solicitarFinalizacionTrabajo(int duracionMinutos, String tipoTecnica) {
        String accion = (tipoTecnica.equals("Pomodoro")) ? "finalizar el tiempo de TRABAJO" : "finalizar la SESIÓN";
        System.out.print("[Simulación: Presione [ENTER] para " + accion + "...]");
        sc.nextLine();
    }

    /**
     * Muestra el mensaje de fin de trabajo y, si es Pomodoro, inicia la simulación de descanso.
     * @param tipoTecnica Nombre de la técnica.
     * @param duracionDescansoMinutos Duración del descanso (5 para Pomodoro, 0 para DeepWork en el controlador).
     */
    public void mostrarFinTrabajoYDescanso(String tipoTecnica, int duracionDescansoMinutos) {
        System.out.println("\n--- ¡TIEMPO DE TRABAJO TERMINADO! ---");
        System.out.println("Sesión registrada. (Avance de la actividad actualizado en base al tiempo).");
        // Lógica específica para Pomodoro (incluye descanso)
        if (tipoTecnica.equals("Pomodoro")) {
            System.out.println("Ahora toma un DESCANSO: " + duracionDescansoMinutos + ":00 minutos restantes");
            System.out.println("Tiempo de Descanso: " + duracionDescansoMinutos + ":00 minutos restantes");
            System.out.print("[Simulación: Presione [ENTER] para finalizar el tiempo de DESCANSO...]");
        sc.nextLine();
        // Para DeepWork, el controlador ya ha finalizado la sesión, así que no se muestra descanso aquí.
        }
    }
}


