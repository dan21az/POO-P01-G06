package com.mycompany.app.estudiantil.controlador;
import com.mycompany.app.estudiantil.vista.MensajeUsuario;
import com.mycompany.app.estudiantil.vista.VistaActividad;
import com.mycompany.app.estudiantil.modelo.actividad.*;

import java.util.*;

public class ControladorActividad {

    private ArrayList<Actividad> listaActividades;
    private VistaActividad vista;

    public ControladorActividad(){
        this.listaActividades = new ArrayList<>();
        this.vista = new VistaActividad();
    }

    public ArrayList<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void menuGestion(){
        int opcion = 0;
        do { 
            opcion = vista.menuGestion();
            switch (opcion) {
                case 1:
                    visualizarActividades();
                    break;
                case 2:
                    crearActividad();
                    break;
                case 3:
                    registrarAvance();
                    break;
                case 4:
                    eliminarActividad();
                    break;
                case 5:
                    vista.mostrarMensaje("Volviendo al menu...");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }

    // --- MÉTODOS ---

    public void visualizarActividades(){

        if (listaActividades.isEmpty()) {
            vista.mostrarMensaje("No hay actividades para mostrar");
            return;
        }
        vista.visualizarActividades(listaActividades);
        // Seleccionar actividad para detalle
        int idSeleccionado = vista.seleccionarActividad("mostrar en detalle");
        if (idSeleccionado != 0) {
            Actividad a = seleccionarActividad(idSeleccionado, listaActividades);
            if (a != null) {
                // Verificar si la actividad seleccionada está en la lista filtrada
                if (listaActividades.contains(a)) {
                    vista.mostrarDetalleActividad(a);
                } else {
                    vista.mostrarMensaje("El ID seleccionado no está disponible");
                }
            } else { 
                vista.mostrarMensaje("ID no válido");
            }
        } else { 
            vista.mostrarMensaje("Regresando al menú...");
        }
    }

    public void crearActividad(){
        int opcionCategoria = vista.seleccionarCategoria();       
        if(opcionCategoria == 1){ // ACADEMICA
            String tipo = seleccionarTipoAcademico();
            String[] d = vista.detallesComunes(); // Nombre, Descripción, F.Vencimiento, Prioridad
            String asignatura = vista.solicitarAsignatura();
            int tiempo = vista.solicitarTiempoEstimado();
            
            MensajeUsuario m = crearActividadAcademica(d[0], d[2], d[3], tipo, tiempo, 0, d[1], asignatura);
            vista.mostrarMensaje(m.toString());
        
        } else if(opcionCategoria == 2){ // PERSONAL
            String tipo = seleccionarTipoPersonal();
            String[] d = vista.detallesComunes();
            String lugar = vista.solicitarLugar();
            int tiempo = vista.solicitarTiempoEstimado();
            
            MensajeUsuario m = crearActividadPersonal(d[0], d[2], d[3], tipo, tiempo, 0, d[1], lugar);
            vista.mostrarMensaje(m.toString());

        } else {
             vista.mostrarMensaje("Opción no válida, volviendo al menú.");
        }
    }

    public void registrarAvance() {
        vista.encabezado("\n--- REGISTRAR AVANCE ---");
        // Obtener solo actividades pendientes (progreso < 100)
        ArrayList<Actividad> pendientes = filtrarYMostrarActividades(listaActividades, "ACADEMICA", true);
        if (pendientes.isEmpty()) {
            vista.mostrarMensaje("No hay actividades pendientes para registrar avance.");
            return;}
        // Mostrar lista y pedir ID
        vista.listarActividadesPendientesParaAvance(pendientes);
        int idSeleccionado = vista.seleccionarActividad("registrar avance");
        if (idSeleccionado != 0) {
            Actividad a = seleccionarActividad(idSeleccionado, pendientes); 
            if (a != null) {
                int nuevoAvance = vista.solicitarNuevoAvance(a);
                
                if (vista.confirmar("el nuevo avance para el proyecto ID " + a.getId() + " es " + nuevoAvance + "%")) {
                    MensajeUsuario m = cambiarProgreso(idSeleccionado, nuevoAvance);
                    vista.mostrarMensaje(m.toString());
                } else {
                    vista.mostrarMensaje("Actualización de avance cancelada.");
                }
            } else {
                 vista.mostrarMensaje("ID no válido o la actividad no está pendiente.");
            }
        } else {
            vista.mostrarMensaje("Regresando al menú...");
        }
    }

    public void eliminarActividad(){
        vista.encabezado("\n--- ELIMINAR ACTIVIDAD ---");
        ArrayList<Actividad> lista = listaActividades;
        if(lista.isEmpty()){
             vista.mostrarMensaje("No hay actividades para eliminar.");
             return;}
        vista.listarActividades(lista); // Mostrar la lista completa de ID y Nombre
        int idSeleccionado = vista.seleccionarActividad("eliminar");
        if (idSeleccionado != 0) {
            Actividad a = seleccionarActividad(idSeleccionado, lista);
            if (a != null) {
                if (vista.confirmar("la eliminación de la actividad '" + a.getNombre() + "' (ID " + a.getId() + ")")) {
                    MensajeUsuario m = eliminaActividad(idSeleccionado);
                    vista.mostrarMensaje(m.toString());
                } else {
                    vista.mostrarMensaje("Eliminación de actividad cancelada.");
                }
            } else {
                vista.mostrarMensaje("ID no válido.");
            }
        } else {
            vista.mostrarMensaje("Regresando al menú...");
        }
    }
    
    public ArrayList<Actividad> filtrarYMostrarActividades(ArrayList<Actividad> lista, String categoria, boolean soloPendientes){
        ArrayList<Actividad> filtradas = new ArrayList<>();
            for (Actividad a : lista) {
                boolean cumpleCategoria = (categoria == null || a.getCategoria().equals(categoria));
                boolean cumpleEstado = (!soloPendientes || a.getProgreso() < 100);
            if (cumpleCategoria && cumpleEstado) {
                filtradas.add(a);
            }
        }
        return filtradas;
    }
    
    public String seleccionarTipoPersonal() {
        String tipo = null;
        do {
            int i = vista.seleccionarTipoPersonal();
            if(i == 1){ tipo = "CITAS"; } 
            else if(i == 2){ tipo = "EJERCICIO"; } 
            else if(i == 3){ tipo = "HOBBIES"; } 
            else { vista.mostrarMensaje("Opción no válida. Intente de nuevo."); }
        } while(tipo == null);
        return tipo;
    }

    public String seleccionarTipoAcademico() {
        String tipo = null;
        do {
            int i = vista.seleccionarTipoAcademico();
            if(i == 1){ tipo = "TAREA"; } 
            else if(i == 2){ tipo = "EXAMEN"; } 
            else if(i == 3){ tipo = "PROYECTO"; } 
            else { vista.mostrarMensaje("Opción no válida. Intente de nuevo."); }
        } while(tipo == null);
        return tipo;
    }

    // Otros métodos //
    public Actividad seleccionarActividad(int i, List <Actividad> lista){
        for(Actividad a: lista){
            if(a.getId() == i){
                return a;
            }
        } 
        return null;
    }

    public MensajeUsuario crearActividadPersonal(String n, String fV, String p, String t, int tE, int pr,String des,String l){
        Actividad.aumentarId();
        int id = Actividad.getContadorId();
        Personal ap = new Personal(n,"PERSONAL",fV, p, t, tE, pr, id,des, l);
        listaActividades.add(ap);
        return new MensajeUsuario("CREACIÓN EXITOSA", "Actividad Personal '" + n + "' creada con éxito.");
    }

    public MensajeUsuario crearActividadAcademica(String n, String fV, String p, String t, int tE, int pr,String des, String asig){
        Actividad.aumentarId();
        int id = Actividad.getContadorId();
        Academica ac = new Academica(n,"ACADEMICA",fV, p, t, tE,pr,id,des, asig);
        listaActividades.add(ac);
        return new MensajeUsuario("CREACIÓN EXITOSA", "Actividad Académica '" + n + "' creada con éxito.");
    }
    
    public MensajeUsuario cambiarProgreso(int id, int nuevoProgreso) {
        Actividad a = seleccionarActividad(id, listaActividades);
        if (a == null){ 
            return new MensajeUsuario("Error", "No existe actividad con ID: " + id);
        } else {
            if (nuevoProgreso < 0 || nuevoProgreso > 100){
                return new MensajeUsuario("Error", "El porcentaje debe estar entre 0 y 100.");
            } else if (nuevoProgreso == 100) {
                 return completarTarea(id);
            } else {
                a.setProgreso(nuevoProgreso);
                a.actualizarEstado();
                return new MensajeUsuario("Progreso actualizado", "Nuevo progreso de '" + a.getNombre() + "': " + nuevoProgreso + "%");
            }
        }
    }

    public MensajeUsuario completarTarea(int id){
        Actividad a = seleccionarActividad(id, listaActividades);
        a.setProgreso(100);
        a.setEstado("COMPLETADO");
        return new MensajeUsuario("Tarea completada", "La actividad '" + a.getNombre() + "' ha sido finalizada.");
    }

    public MensajeUsuario eliminaActividad(int id) {
        Actividad a = seleccionarActividad(id, listaActividades);
        if (a == null) {
            return new MensajeUsuario("Error", "ID no válido o actividad no encontrada.");
        } else {
            listaActividades.remove(a);
            return new MensajeUsuario("Eliminación exitosa", "Actividad '" + a.getNombre() + "' eliminada.");
        }
    }
    
}
