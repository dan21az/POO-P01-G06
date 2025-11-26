package com.mycompany.app.estudiantil.controlador;

import com.mycompany.app.estudiantil.modelo.actividad.*;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;
import com.mycompany.app.estudiantil.vista.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class ControladorSesionEnfoque {

    private ArrayList<SesionEnfoque> sesionesEnfoque;
    private VistaSesionEnfoque vista;
    private VistaActividad vistaA;
    private ControladorActividad cA;
    

    //Constructor
    public ControladorSesionEnfoque(ControladorActividad cA){
        this.vista = new VistaSesionEnfoque();
        this.vistaA = new VistaActividad();
        this.sesionesEnfoque = new ArrayList<>();
        this.cA = cA;
    }

    public ArrayList<SesionEnfoque> getSesionesEnfoque() {
        return sesionesEnfoque;
    }

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
    
    public void iniciarPomodoro(){
        vista.encabezado("-- INICIAR POMODORO ---");
    // Filtrar actividades académicas pendientes
        ArrayList<Actividad> pendientes = cA.filtrarYMostrarActividades(cA.getListaActividades(), "ACADEMICA", true); 
        if (pendientes.isEmpty()) {
            vista.mostrarMensaje("No hay actividades académicas pendientes para iniciar una sesión.");
            return; }
        // ... (lógica de selección y validación)
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
            // 1. INICIO DEL TRABAJO
                vista.mostrarInicioTrabajo(a.getNombre(), p.getTipoTecnica(), p.getCicloActual(), p.getCiclos(), 25); 
            // 2. PAUSA DE SIMULACIÓN (Solicita ENTER para terminar)
                vista.solicitarFinalizacionTrabajo(25, p.getTipoTecnica());
            // 3. LÓGICA DE NEGOCIO (Guardar Progreso)
                finalizarSesion(p); // Llama a guardarProgreso()
            // 4. FIN DE TRABAJO E INICIO DE DESCANSO
            // El método de la vista gestiona el mensaje de fin de trabajo, el guardado y el prompt de ENTER para el descanso.
                vista.mostrarFinTrabajoYDescanso(p.getTipoTecnica(), 5);     
        } else {
            vista.mostrarMensaje("ID no válido o la actividad no es académica/pendiente.");
        }
        } else {
            vista.mostrarMensaje("Regresando al menú...");
        }
    }

    public void iniciarDeepWork(){
        vista.encabezado("-- INICIAR DEEP WORK ---");
        ArrayList<Actividad> pendientes = cA.filtrarYMostrarActividades(cA.getListaActividades(), "ACADEMICA", true);
        if (pendientes.isEmpty()) {
            vista.mostrarMensaje("No hay actividades académicas pendientes para iniciar una sesión.");
            return;
        }
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
                // 1. INICIO DEL TRABAJO (90 minutos)
                vista.mostrarInicioTrabajo(a.getNombre(), d.getTipoTecnica(), 1, 1, 90);
                // 2. PAUSA DE SIMULACIÓN (Solicita ENTER para terminar)
                vista.solicitarFinalizacionTrabajo(90, d.getTipoTecnica());
                // 3. LÓGICA DE NEGOCIO (Guardar Progreso)
                finalizarSesion(d); // Llama a guardarProgreso()
                // 4. MENSAJE FINAL (No hay descanso)
                vista.mostrarFinTrabajoYDescanso(d.getTipoTecnica(), 0);
                vista.mostrarMensaje("Sesión de Deep Work completada."); // Mensaje final limpio   
            } else {
                vista.mostrarMensaje("ID no válido o la actividad no es académica/pendiente.");
            }
        } else {
        vista.mostrarMensaje("Regresando al menú...");
        }
    }
    
    public MensajeUsuario crearPomodoro(String fecha, Actividad actividad){
        Pomodoro p = new Pomodoro(fecha, actividad, 4);
        sesionesEnfoque.add(p);
        if (actividad instanceof Academica) {
        ((Academica) actividad).añadirSesion(p);
    }
        return new MensajeUsuario("Pomodoro", "Iniciado con éxito");
    }

    public MensajeUsuario crearDeepWork(String fecha, Actividad actividad){
        DeepWork d = new DeepWork(fecha, actividad);
        sesionesEnfoque.add(d);
        if (actividad instanceof Academica) {
        ((Academica) actividad).añadirSesion(d);
    }
        return new MensajeUsuario("DeepWork ", "Iniciado con éxito");
    }

    public void finalizarSesion(SesionEnfoque s){
        guardarProgreso(s);
    }

    public void guardarProgreso(SesionEnfoque s){
        int t = 100;
        cA.cambiarProgreso(s.getActividad().getId(),t);
        s.getActividad().actualizarEstado();
    
    }
    
    public String fecha(){
    LocalDate hoy = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMM");
        String fecha = hoy.format(fmt);
        return fecha;
    }

}
