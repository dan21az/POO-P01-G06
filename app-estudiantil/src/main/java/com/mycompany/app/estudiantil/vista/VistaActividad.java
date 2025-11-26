package com.mycompany.app.estudiantil.vista;

import java.util.List;
import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.actividad.*;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;

public class VistaActividad {

    private Scanner sc;

    public VistaActividad(){
        this.sc = new Scanner(System.in);
    }
    
    // --- GENERALES ---
    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje); 
        System.out.print("Presione [ENTER] para continuar...");
        sc.nextLine();
    }
    
    public boolean confirmar(String accion){
        System.out.print("¿Confirma que " + accion + "? (S/N): ");
        String respuesta = sc.nextLine().trim().toUpperCase();
        return respuesta.equals("S");
    }

    public void encabezado(String encabezado){
        System.out.println(encabezado);
        System.out.println("Actividades disposibles: ");
    }

    public int menuGestion(){
        int opcion = 0;
        System.out.println("\n--- GESTIÓN DE ACTIVIDADES ---");
        System.out.println("1. Visualizar actividades");
        System.out.println("2. Crear actividad");
        System.out.println("3. Registrar avance de actividad");
        System.out.println("4. Eliminar Actividad");
        System.out.println("5. Volver al menú");
        System.out.print("Seleccione una opción: ");
        
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            opcion = -1; // Opción inválida
        }
        return opcion;
    }
    
    // --- CREAR ACTIVIDAD (Secciones) ---
    public int seleccionarCategoria(){
        System.out.println("\n=========================================");
        System.out.println("      C R E A R  A C T I V I D A D");
        System.out.println("=========================================");
        System.out.println("\n--- PASO 1: CATEGORÍA ---");
        System.out.println("Seleccione la categoría de la actividad:");
        System.out.println("1. ACADÉMICA (Tarea, Examen, Proyecto)");
        System.out.println("2. PERSONAL (Citas, Ejercicio, Hobbies)");      
        System.out.print("Ingrese opción (1 o 2): ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public String[] detallesComunes(){
        String[] detalles = new String[4];
        System.out.println("\n--- PASO 3: DETALLES ---");
        System.out.print("Ingrese el Nombre de la Actividad: ");
        detalles[0] = sc.nextLine();
        System.out.print("Ingrese la Descripción: ");
        detalles[1] = sc.nextLine();  
        System.out.print("Ingrese la Fecha de Vencimiento (DD/MM/AAAA HH:mm): ");
        detalles[2] = sc.nextLine();
        System.out.print("Ingrese Prioridad (ALTA, MEDIA, BAJA): ");
        detalles[3] = sc.nextLine().toUpperCase();
        return detalles;
    }
    
    public int solicitarTiempoEstimado(){
        System.out.print("Ingrese Tiempo Estimado (en horas): "); // Cambiado a horas como en el ejemplo
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int seleccionarTipoAcademico(){
        System.out.println("\n--- PASO 2: TIPO (Académica) ---");
        System.out.println("Ha seleccionado: ACADÉMICA.");
        System.out.println("1. TAREA");
        System.out.println("2. EXAMEN");
        System.out.println("3. PROYECTO");
        System.out.print("Ingrese opción (1-3): ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int seleccionarTipoPersonal(){
        System.out.println("\n--- PASO 2: TIPO (Personal) ---");
        System.out.println("Ha seleccionado: PERSONAL.");
        System.out.println("1. CITAS");
        System.out.println("2. EJERCICIO");
        System.out.println("3. HOBBIES");
        System.out.print("Ingrese opción (1-3): ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String solicitarLugar(){
        System.out.print("Ingrese el Lugar (Opcional): ");
        return sc.nextLine();
    }

    public String solicitarAsignatura(){
        System.out.print("Ingrese la Asignatura: ");
        return sc.nextLine();
    }
    
    // --- VISUALIZAR ACTIVIDADES (Opcion 1) ---
    public void visualizarActividades(List <Actividad> lista){
        
        System.out.println("\n--- LISTADO DE ACTIVIDADES ---\n");

        System.out.printf("%-3s | %-10s | %-10s | %-30s | %-18s | %-8s | %-8s\n", "ID", "CATEGORIA", "TIPO", "NOMBRE", "VENCIMIENTO", "PRIORIDAD", "PROGRESO (%)");
        System.out.println("---|------------|------------|--------------------------------|--------------------|----------|------------");
        
        for(Actividad a : lista){
            System.out.printf("%-3d | %-10s | %-10s | %-30s | %-18s | %-8s | %-8d\n",
                a.getId(), a.getCategoria(), a.getTipo(), a.getNombre(), a.getFechaVencimiento(), a.getPrioridad(), a.getProgreso());
        }
    }

    public int seleccionarActividad (String uso){
        System.out.print("\nSeleccionar el ID de una actividad para "+uso+" (o 0 para regresar): ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // --- DETALLE ACTIVIDAD ---
    public void mostrarDetalleActividad(Actividad a){
        System.out.println("\n=========================================");
        System.out.println("      DETALLES DE LA ACTIVIDAD (ID " + a.getId() + ")");
        System.out.println("=========================================");
        System.out.println("Nombre: " + a.getNombre());
        System.out.println("Tipo: " + a.getTipo());
        System.out.println("Prioridad: " + a.getPrioridad());
        System.out.println("Estado: " + a.getEstado());
        System.out.println("Fecha Límite: " + a.getFechaVencimiento());
        System.out.println("Tiempo Estimado Total: " + a.getTiempoEstimado() + " horas");
        System.out.println("Avance Actual: " + a.getProgreso() + "%");
        System.out.println("Descripción: " + a.getDescripción());
        if(a.getCategoria().equals("ACADEMICA")){
            Academica b = (Academica) a;
            detallesAcademica(b);
        } else if(a.getCategoria().equals("PERSONAL")){
            Personal c = (Personal) a;
            detallesPersonal(c);
        }
        
        System.out.println("Presione [ENTER] para volver a la lista...");
        sc.nextLine();
    }
    
    public void detallesAcademica(Academica b){
        System.out.println("Asignatura: "+b.getAsignatura());
        
        if(!b.getSesiones().isEmpty()){
            System.out.println("---------------------------------");
            System.out.println("HISTORIAL DE GESTIÓN DEL TIEMPO");
            System.out.println("---------------------------------");
            System.out.printf("| %-12s | %-18s | %-12s |\n", "Fecha Sesión", "Técnica Aplicada", "Duración (min)");
            System.out.println("|--------------|--------------------|--------------|");
            for(SesionEnfoque s : b.getSesiones()){
                System.out.printf("| %-12s | %-18s | %-12s |\n", s.getFecha(), s.getTipoTecnica(), s.getDuracionEnfoque());
            }
            System.out.println("---------------------------------");
        }
    }

    public void detallesPersonal(Personal c){
        System.out.println("Lugar: "+ c.getLugar());
    }
    
    // --- REGISTRAR AVANCE (Opción 3) ---
    public void listarActividadesPendientesParaAvance(List<Actividad> pendientes){
        System.out.printf("%-3s | %-10s | %-40s | %-8s\n","ID", "TIPO", "NOMBRE", "AVANCE (%)");
        System.out.println("---|------------|------------------------------------------|-----------");
        
        for(Actividad a : pendientes){
            System.out.printf("%-3d | %-10s | %-40s | %-8d%%\n", a.getId(), a.getTipo(), a.getNombre(), a.getProgreso());
        }
    }

    public int solicitarNuevoAvance(Actividad a){
        System.out.println("\nHa seleccionado: "+a.getTipo()+" '" + a.getNombre() + "'.");
        System.out.println("Avance actual: "+a.getProgreso() + "%.");
        System.out.print("Ingrese el nuevo porcentaje de avance (0 - 100): ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // --- ELIMINAR ACTIVIDAD (Opción 4) ---
    public void listarActividades(List<Actividad> lista){
        System.out.printf("%-3s | %-10s | %-40s\n", "ID", "TIPO", "NOMBRE");
        System.out.println("---|------------|------------------------------------------");
        for(Actividad a : lista){
            System.out.printf("%-3d | %-10s | %-40s\n",
                a.getId(), a.getTipo(), a.getNombre());
        }
    }
}
