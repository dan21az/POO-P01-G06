package com.mycompany.app.estudiantil.controlador;

import com.mycompany.app.estudiantil.modelo.actividad.*;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;
import com.mycompany.app.estudiantil.vista.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Clase ControladorSesionEnfoque
 * Gestiona el inicio y la finalización de sesiones de enfoque (Pomodoro y Deep Work)
 * asociadas a actividades académicas pendientes.
 * Se comunica con el ControladorActividad para obtener y actualizar la información de las tareas.
 */
public class ControladorSesionEnfoque {

    // Lista para almacenar todas las sesiones de enfoque realizadas.
    private ArrayList<SesionEnfoque> sesionesEnfoque;
    private VistaSesionEnfoque vista;
    private VistaActividad vistaA;
    // Referencia al ControladorActividad para acceder a la lista de actividades y métodos de utilidad (filtrar, seleccionar).
    private ControladorActividad cA;
    

    /**
     * Constructor de la clase ControladorSesionEnfoque.
     * @param cA Instancia del ControladorActividad, necesaria para acceder a los datos de las tareas.
     */
    public ControladorSesionEnfoque(ControladorActividad cA){
        this.vista = new VistaSesionEnfoque();
        this.vistaA = new VistaActividad();
        this.sesionesEnfoque = new ArrayList<>();
        this.cA = cA;
    }

    /**
     * Obtiene la lista de sesiones de enfoque registradas.
     * @return ArrayList<SesionEnfoque> La lista de sesiones.
     */
    public ArrayList<SesionEnfoque> getSesionesEnfoque() {
        return sesionesEnfoque;
    }

    /**
     * Muestra el menú de gestión de sesiones de enfoque y procesa la opción seleccionada.
     */
    public void menuSesion(){
        int opcion = 0;
        do {
        opcion = vista.menuSesion();
        switch (opcion) {
            case 1:
                iniciarPomodoro();
                break;
            case 2:
                iniciarDeepWork();
                break;
            case 3:
                vista.mostrarMensaje("Regresando al menú...");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        } while (opcion != 3);
    }
    
    /**
     * Prepara e inicia una simulación de sesión Pomodoro (25 minutos de trabajo, 5 minutos de descanso).
     * Solo permite seleccionar actividades académicas pendientes.
     */
    public void iniciarPomodoro(){
        vista.encabezado("-- INICIAR POMODORO ---");
        // Filtrar actividades académicas pendientes
        ArrayList<Actividad> pendientes = cA.filtrarActividades(cA.getListaActividades(), "ACADEMICA", true); 
        if (pendientes.isEmpty()) {
            vista.mostrarMensaje("No hay actividades académicas pendientes para iniciar una sesión.");
            return; }
        // Mostrar la lista y solicitar ID
        vistaA.listarActividades(pendientes);
        int idSeleccionado = vistaA.seleccionarActividad("a trabajar");
        if (idSeleccionado != 0) {
            Actividad a = cA.seleccionarActividad(idSeleccionado, pendientes);      
            if (a != null && a instanceof Academica) {       
                MensajeUsuario m = crearPomodoro(fecha(), a);
                vista.mostrarMensaje(m.toString());
                Academica b = (Academica) a;
                SesionEnfoque s = b.getSesiones().get(b.getSesiones().size()-1);
                Pomodoro p = (Pomodoro) s;
            // INICIO DEL TRABAJO
<<<<<<< HEAD
            // SIMULACIÓN DEL CICLO DE TRABAJO
=======
<<<<<<< HEAD
            // SIMULACIÓN DEL CICLO DE TRABAJO
                vista.mostrarInicioTrabajo(a.getNombre(), p.getTipoTecnica(), p.getCicloActual(), p.getCiclos(), 25); 
            // PAUSA DE SIMULACIÓN Espera una acción del usuario (ENTER) para simular el fin del tiempo.
                vista.solicitarFinalizacionTrabajo(25, p.getTipoTecnica());
            // Finalizar la sesión (registra el avance por defecto, 25%).
=======
>>>>>>> f2ea20c2d234438c08735be1adc91a6c62c237e4
                vista.mostrarInicioTrabajo(a.getNombre(), p.getTipoTecnica(), p.getCicloActual(), p.getCiclos(), 25); 
            // PAUSA DE SIMULACIÓN Espera una acción del usuario (ENTER) para simular el fin del tiempo.
                vista.solicitarFinalizacionTrabajo(25, p.getTipoTecnica());
<<<<<<< HEAD
            // Finalizar la sesión (registra el avance por defecto, 25%).
=======
>>>>>>> d719903cf6984712fbe696a37151fc3c15aad508
>>>>>>> f2ea20c2d234438c08735be1adc91a6c62c237e4
                finalizarSesion(p);
            // FIN DE TRABAJO E INICIO DE DESCANSO.
                vista.mostrarFinTrabajoYDescanso(p.getTipoTecnica(), 5);     
        } else {
            vista.mostrarMensaje("ID no válido o la actividad no es académica/pendiente.");
        }
        } else {
            vista.mostrarMensaje("Regresando al menú...");
        }
    }

    /**
     * Prepara e inicia una simulación de sesión Deep Work (90 minutos de trabajo sin descanso).
     * Solo permite seleccionar actividades académicas pendientes.
     */
    public void iniciarDeepWork(){
        // Filtrar solo actividades académicas pendientes.
        vista.encabezado("-- INICIAR DEEP WORK ---");
        ArrayList<Actividad> pendientes = cA.filtrarActividades(cA.getListaActividades(), "ACADEMICA", true);
        if (pendientes.isEmpty()) {
            vista.mostrarMensaje("No hay actividades académicas pendientes para iniciar una sesión.");
            return;
        }
        // Mostrar la lista y solicitar ID.
        vistaA.listarActividades(pendientes);
        int idSeleccionado = vistaA.seleccionarActividad("a trabajar");
        if (idSeleccionado != 0) {
            Actividad a = cA.seleccionarActividad(idSeleccionado, pendientes);
            if (a != null && a instanceof Academica) {
                MensajeUsuario m = crearDeepWork(fecha(), a);
                vista.mostrarMensaje(m.toString());
                Academica b = (Academica) a;
                SesionEnfoque s = b.getSesiones().get(b.getSesiones().size()-1);
                DeepWork d = (DeepWork) s;
                // INICIO DEL TRABAJO (90 minutos)
                vista.mostrarInicioTrabajo(a.getNombre(), d.getTipoTecnica(), 1, 1, 90);
                // PAUSA DE SIMULACIÓN (Solicita ENTER para terminar)
                vista.solicitarFinalizacionTrabajo(90, d.getTipoTecnica());
<<<<<<< HEAD
                // Finalizar la sesión (registra el avance por defecto, 25%).
                finalizarSesion(d); 
                // Muestra el fin del trabajo (tiempo de descanso es 0 en Deep Work).
=======
<<<<<<< HEAD
                // Finalizar la sesión (registra el avance por defecto, 25%).
                finalizarSesion(d); 
                // Muestra el fin del trabajo (tiempo de descanso es 0 en Deep Work).
=======
                finalizarSesion(d); 
                // MENSAJE FINAL (No hay descanso)
>>>>>>> d719903cf6984712fbe696a37151fc3c15aad508
>>>>>>> f2ea20c2d234438c08735be1adc91a6c62c237e4
                vista.mostrarFinTrabajoYDescanso(d.getTipoTecnica(), 0);
                vista.mostrarMensaje("Sesión de Deep Work completada.");
            } else {
                vista.mostrarMensaje("ID no válido o la actividad no es académica/pendiente.");
            }
        } else {
        vista.mostrarMensaje("Regresando al menú...");
        }
    }
    
    /**
     * Crea una nueva sesión Pomodoro, la añade a la lista general y la asocia a la actividad.
     * @param fecha Fecha de inicio de la sesión.
     * @param actividad Actividad a la que se asocia la sesión.
     * @return MensajeUsuario con el resultado.
     */
    public MensajeUsuario crearPomodoro(String fecha, Actividad actividad){
        // Pomodoro se inicializa con 4 ciclos por defecto (regla clásica).
        Pomodoro p = new Pomodoro(fecha, actividad, 4);
        sesionesEnfoque.add(p);
        // Si la actividad es Académica, se añade la sesión a su lista interna.
        if (actividad instanceof Academica) {
        ((Academica) actividad).añadirSesion(p);
    }
        return new MensajeUsuario("Pomodoro", "Iniciado con éxito");
    }

    /**
     * Crea una nueva sesión Deep Work, la añade a la lista general y la asocia a la actividad.
     * @param fecha Fecha de inicio de la sesión.
     * @param actividad Actividad a la que se asocia la sesión.
     * @return MensajeUsuario con el resultado.
     */
    public MensajeUsuario crearDeepWork(String fecha, Actividad actividad){
        DeepWork d = new DeepWork(fecha, actividad);
        sesionesEnfoque.add(d);
        // Si la actividad es Académica, se añade la sesión a su lista interna.
        if (actividad instanceof Academica) {
        ((Academica) actividad).añadirSesion(d);
    }
        return new MensajeUsuario("DeepWork ", "Iniciado con éxito");
    }

    /**
     * Llama al método de la sesión para registrar su finalización.
     * Por defecto, añade un 25% de progreso a la actividad asociada.
     * @param s La sesión de enfoque que finaliza.
     */
    public void finalizarSesion(SesionEnfoque s){
<<<<<<< HEAD
        int i = 25; // Define el progreso por defecto que añade una sesión completa.
        s.finalizarSesion(i); // Este método dentro de SesionEnfoque actualizará el progreso de la Actividad asociada.
    }

    /**
     * Obtiene la fecha actual en formato "d MMM" (ej: 27 Nov).
     * @return String con la fecha formateada.
     */
=======
<<<<<<< HEAD
        int i = 25; // Define el progreso por defecto que añade una sesión completa.
        s.finalizarSesion(i); // Este método dentro de SesionEnfoque actualizará el progreso de la Actividad asociada.
    }

    /**
     * Obtiene la fecha actual en formato "d MMM" (ej: 27 Nov).
     * @return String con la fecha formateada.
     */
=======
        //Extrae la actividad
        Actividad a = s.getActividad();
        //Actualiza el progreso segun el porcentaje, como es una simulación guarda el 25% por defecto
        int i = a.getProgreso() + 25;
        cA.cambiarProgreso(a.getId(),i);
        a.actualizarEstado();
    }

    
>>>>>>> d719903cf6984712fbe696a37151fc3c15aad508
>>>>>>> f2ea20c2d234438c08735be1adc91a6c62c237e4
    public String fecha(){
    LocalDate hoy = LocalDate.now();
    // Definir el formato de salida.
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMM");
        String fecha = hoy.format(fmt);
        return fecha;
    }

}
