package com.mycompany.app.estudiantil.controlador;
import com.mycompany.app.estudiantil.vista.MensajeUsuario;
import com.mycompany.app.estudiantil.vista.VistaActividad;
import com.mycompany.app.estudiantil.modelo.actividad.*;

import java.util.*;

/**
 * Clase ControladorActividad
 * Actúa como el controlador en el patrón MVC, manejando la lógica de la aplicación
 * estudiantil para la gestión de actividades (Académicas y Personales).
 * Interactúa con el Modelo (Actividad, Academica, Personal) y la Vista (VistaActividad, MensajeUsuario).
 */
public class ControladorActividad {

    // Lista principal para almacenar todas las actividades creadas.
    private ArrayList<Actividad> listaActividades;
    // Instancia de la clase Vista para manejar la interacción con el usuario.
    private VistaActividad vista;

    /**
     * Constructor de la clase ControladorActividad.
     * Inicializa la lista de actividades y la instancia de la vista.
     */
    public ControladorActividad(){
        this.listaActividades = new ArrayList<>();
        this.vista = new VistaActividad();
    }

    /**
     * Obtiene la lista completa de actividades.
     * @return ArrayList<Actividad> La lista de actividades.
     */
    public ArrayList<Actividad> getListaActividades() {
        return listaActividades;
    }

    /**
     * Muestra el menú de gestión principal y procesa las opciones seleccionadas por el usuario.
     */
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

// --- Métodos del menú ---
    
    /**
     * Muestra todas las actividades y permite al usuario seleccionar una para ver su detalle.
     */
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

    /**
     * Guía al usuario a través del proceso de creación de una nueva actividad
     * (Académica o Personal) y la añade a la lista.
     */
    public void crearActividad(){
        int opcionCategoria = vista.seleccionarCategoria();       
        if(opcionCategoria == 1){ // ACADEMICA
            String tipo = seleccionarTipoAcademico();
            String[] d = vista.detallesComunes(); // Nombre, Descripción, F.Vencimiento, Prioridad
            String asignatura = vista.solicitarAsignatura();
            int tiempo = vista.solicitarTiempoEstimado();
            
            MensajeUsuario m = crearActividadAcademica(d[0], d[2], d[3], tipo, tiempo, 0,"No iniciada" ,d[1], asignatura);
            vista.mostrarMensaje(m.toString());
        
        } else if(opcionCategoria == 2){ // PERSONAL
            String tipo = seleccionarTipoPersonal();
            String[] d = vista.detallesComunes();
            String lugar = vista.solicitarLugar();
            int tiempo = vista.solicitarTiempoEstimado();
            
            MensajeUsuario m = crearActividadPersonal(d[0], d[2], d[3], tipo, tiempo, 0, "No Iniciada",d[1], lugar);
            vista.mostrarMensaje(m.toString());
    
        } else {
             vista.mostrarMensaje("Opción no válida, volviendo al menú.");
        }
    }

    /**
     * Permite al usuario seleccionar una actividad pendiente y actualizar su porcentaje de progreso.
     */
    public void registrarAvance() {
        vista.encabezado("\n--- REGISTRAR AVANCE ---");
        // Obtener solo actividades pendientes (progreso < 100)
        ArrayList<Actividad> pendientes = filtrarActividades(listaActividades, "ACADEMICA", true);
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
                if (nuevoAvance > 0 && nuevoAvance <= 100){
                if (vista.confirmar("el nuevo avance para el proyecto ID " + a.getId() + " es " + nuevoAvance + "%")) {
                    MensajeUsuario m = actualizarAvance(a, nuevoAvance);
                    vista.mostrarMensaje(m.toString());
                } else {
                    vista.mostrarMensaje("Actualización de avance cancelada.");
                }} else {vista.mostrarMensaje("Error el porcentaje debe estar entre 0 y 100 ");;}
            } else {
                 vista.mostrarMensaje("ID no válido o la actividad no está pendiente.");
            }
        } else {
            vista.mostrarMensaje("Regresando al menú...");
        }
    }

    /**
     * Permite al usuario seleccionar una actividad para eliminarla de la lista.
     */
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
                    MensajeUsuario m = eliminaActividad(a);
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
    

    /**
     * Muestra las opciones y solicita al usuario el tipo de actividad Personal.
     * @return El String del tipo de actividad Personal seleccionado.
     */
    public ArrayList<Actividad> filtrarActividades(ArrayList<Actividad> lista, String categoria, boolean soloPendientes){
        // Recorrer la lista original
        ArrayList<Actividad> filtradas = new ArrayList<>();
            for (Actividad a : lista) {
                // Comprobar si cumple el criterio de categoría.
                boolean cumpleCategoria = (categoria == null || a.getCategoria().equals(categoria));
                // Comprobar si cumple el criterio de estado (progreso < 100)
                boolean cumpleEstado = (!soloPendientes || a.getProgreso() < 100);
                // Si cumple ambos, agregarla a la lista filtrada.
            if (cumpleCategoria && cumpleEstado) {
                filtradas.add(a);
            }
        }
        return filtradas;
    }
    
    /**
     * Muestra las opciones y solicita al usuario el tipo de actividad Personal.
     * @return El String del tipo de actividad Personal seleccionado.
     */
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

    /**
     * Muestra las opciones y solicita al usuario el tipo de actividad Académica.
     * @return El String del tipo de actividad Académica seleccionado.
     */
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


    /**
     * Busca y retorna una actividad específica por su ID dentro de una lista dada.
     * @param i El ID de la actividad a buscar.
     * @param lista La lista en la que se buscará la actividad.
     * @return La actividad encontrada o null si no se encuentra.
     */
    public Actividad seleccionarActividad(int i, List <Actividad> lista){
        for(Actividad a: lista){
            if(a.getId() == i){
                return a;
            }
        } 
        return null;
    }

    /**
     * Crea una nueva instancia de Actividad Personal, le asigna un ID, la agrega a la lista
     * y retorna un mensaje de éxito.
     */
    public MensajeUsuario crearActividadPersonal(String n, String fV, String p, String t, int tE, int pr, String est ,String des,String l){
        // Incrementar el contador estático de ID.
        Actividad.aumentarId();
        int id = Actividad.getContadorId();
        // Crear la nueva instancia de Personal.
        Personal ap = new Personal(n,"PERSONAL",fV, p, t, tE, pr, id, est,des, l);
        listaActividades.add(ap);
        return new MensajeUsuario("CREACIÓN EXITOSA", "Actividad Personal '" + n + "' creada con éxito.");
    }

    /**
     * Crea una nueva instancia de Actividad Académica, le asigna un ID, la agrega a la lista
     * y retorna un mensaje de éxito.
     */
    public MensajeUsuario crearActividadAcademica(String n, String fV, String p, String t, int tE, int pr, String est ,String des, String asig){
        // Incrementar el contador estático de ID.
        Actividad.aumentarId();
        int id = Actividad.getContadorId();
        // Crear la nueva instancia de Academica.
        Academica ac = new Academica(n,"ACADEMICA",fV, p, t, tE,pr,id, est,des, asig);
        listaActividades.add(ac);
        return new MensajeUsuario("CREACIÓN EXITOSA", "Actividad Académica '" + n + "' creada con éxito.");
    }
    
    /**
     * Actualiza el progreso de una actividad específica.
     * @param a La actividad cuyo progreso será actualizado.
     * @param nuevoProgreso El nuevo porcentaje de progreso (1 a 100).
     * @return MensajeUsuario con el resultado de la operación.
     */

    public MensajeUsuario actualizarAvance(Actividad a, int nuevoProgreso) {
        if (a == null){ 
            return new MensajeUsuario("Error", "No existe actividad ese ID: ");
        } else {
            if (nuevoProgreso <= 0 || nuevoProgreso >= 100){
                return new MensajeUsuario("Error", "El porcentaje debe estar entre 0 y 100.");
            } else if (nuevoProgreso == 100) {
                a.actualizarAvance(nuevoProgreso);
                 return new MensajeUsuario("Tarea completada", "La actividad '" + a.getNombre() + "' ha sido finalizada.");
            } else {
                a.actualizarAvance(nuevoProgreso);
                return new MensajeUsuario("Progreso actualizado", "Nuevo progreso de '" + a.getNombre() + "': " + nuevoProgreso + "%");
            }
        }
    }

    /**
     * Elimina una actividad de la lista principal.
     * @param a La actividad a eliminar.
     * @return MensajeUsuario con el resultado de la operación.
     */

    public MensajeUsuario eliminaActividad(Actividad a) {
        if (a == null) {
            return new MensajeUsuario("Error", "ID no válido o actividad no encontrada.");
        } else {
            listaActividades.remove(a);
            return new MensajeUsuario("Eliminación exitosa", "Actividad '" + a.getNombre() + "' eliminada.");
        }
    }
    
}
