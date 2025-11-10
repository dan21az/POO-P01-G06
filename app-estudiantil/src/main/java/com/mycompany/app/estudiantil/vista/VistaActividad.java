package com.mycompany.app.estudiantil.vista;

import java.util.List;
import java.util.Scanner;

import com.mycompany.app.estudiantil.controlador.*;
import com.mycompany.app.estudiantil.modelo.actividad.*;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.*;

public class VistaActividad {

    private ControladorActividad cA;
    private Scanner sc;

    public VistaActividad(ControladorActividad cA){
        this.cA = cA;
        this.sc = new Scanner(System.in);
    }

    public void menuGestion(){
        int opcion = 0;
        do {
            System.out.println("--- GESTIÓN DE ACTIVIDADES ---");
            System.out.println("1. Visualizar actividades");
            System.out.println("2. Crear actividad");
            System.out.println("3. Registrar avance de actividad");
            System.out.println("4. Eliminar Actividad");
            System.out.println("5. Volver al menú");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                visualizarActividades(cA.getListaActividades());
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
                System.out.println("Volviendo al menu...");
                break;
            default:
                System.out.println("Opcion no valida");
                menuGestion();
        }
        } while (opcion != 5);
    }

    public void crearActividad(){
        System.out.println("--- PASO 1: CATEGORÍA ---");
        System.out.println("Seleccione la categoría de la actividad: ");
        System.out.println("1. ACADÉMICA (Tarea, Examen, Proyecto)");
        System.out.println("2. PERSONAL (Citas, Ejercicio, Hobbies)");      
        System.out.print("Ingrese opción (1 o 2): ");
        int i = sc.nextInt();
        sc.nextLine();
        if(i==1){
            String tipo = seleccionarTipoAcademico();
            String[] d = detallesPaso3();
            System.out.print("Ingrese la Asignatura: ");
            String asignatura = sc.nextLine();
            int tiempo = indicarTiempo();
            MensajeUsuario m = cA.crearActividadAcademica(d[0], d[2], d[3], tipo, tiempo,d[1], asignatura);
            mostrarMensaje(m.toString());
        } else if(i==2){
            String tipo = seleccionarTipoPersonal();
            String[] d = detallesPaso3(); 
            System.out.print("Ingrese el Lugar: ");
            String lugar = sc.nextLine();
            int tiempo = indicarTiempo();
            MensajeUsuario m = cA.crearActividadPersonal(d[0], d[2], d[3], tipo, tiempo, d[1], lugar);
            mostrarMensaje(m.toString());
        }
    }

    public int indicarTiempo(){
        System.out.print("Ingrese Tiempo Estimado (en horas): ");
        int tiempo = sc.nextInt();
        sc.nextLine();
        return tiempo;
    }

    public String[] detallesPaso3(){
        String[] detalles = new String[4];
        System.out.println("-- PASO 3: DETALLES ---");
        System.out.print("Ingrese el Nombre: ");
        String nombre = sc.nextLine();
        detalles[0] = nombre;
        System.out.print("Ingrese la Descripción: ");
        String descripcion = sc.nextLine();
        detalles[1] = descripcion; 
        System.out.print("Ingrese la Fecha de Vencimiento (DD/MM/AAAA): ");
        String fVencimiento = sc.nextLine();
        detalles[2] = fVencimiento;
        System.out.print("Ingrese Prioridad (ALTA, MEDIA, BAJA): ");
        String prioridad = sc.nextLine();
        detalles[3] = prioridad;
        return detalles;
    }

    public String seleccionarTipoAcademico(){
        System.out.println("--- PASO 2: TIPO (Académica) ---");
        System.out.println("Ha seleccionado: ACADÉMICA.");
        System.out.println("1. TAREA");
        System.out.println("2. EXAMEN");
        System.out.println("3. PROYECTO");
        System.out.print("Ingrese opción (1-3): ");
        int iTipo = sc.nextInt();
        sc.nextLine();
        String tipo = null;
        if(iTipo ==1){
                tipo = "TAREA";
            } else if(iTipo==2){
                tipo = "EXAMEN";
            } else if(iTipo ==3){
                tipo ="PROYECTO";
            } else {
                System.out.println("No valido");
            }
        return tipo;
    }

    public String seleccionarTipoPersonal(){
        System.out.println("--- PASO 2: TIPO (Personal) ---");
        System.out.println("Ha seleccionado: ACADÉMICA.");
        System.out.println("1. CITAS");
        System.out.println("2. EJERCICIO");
        System.out.println("3. HOBBIES");
        System.out.print("Ingrese opción (1-3): ");
        int iTipo = sc.nextInt();
        sc.nextLine();
        String tipo = null;
        if(iTipo ==1){
            tipo = "CITAS";
        } else if(iTipo==2){
            tipo = "EJERCICIO";
        } else if(iTipo ==3){
            tipo ="HOBBIES";
        } else {
            System.out.println("No valido");
        }
        return tipo;
    }

    public void mostrarListaActividades(List<Actividad> actividades){
        if(actividades.size()>0){
            System.out.println("ID"+"\t"+"Nombre");
            for(Actividad a: actividades){
                System.out.println(a.getId()+"\t"+a.getNombre());
            }
        } else{
            mostrarMensaje("No hay actividad para mostrar");
            sc.nextLine();
        }
    }

    public void detalleActividad(Actividad a){
        System.out.println("Nombre: "+a.getNombre());
        System.out.println("Categoria: "+a.getCategoria());
        System.out.println("Tipo: "+a.getTipo());
        System.out.println("Prioridad: "+a.getPrioridad());
        System.out.println("Estado: "+a.getEstado());
        System.out.println("Fecha Limite: "+a.getFechaVencimiento());
        System.out.println("Tiempo Estimado Total: "+a.getTiempoEstimado());
        System.out.println("Avance Actual: "+a.getProgreso());
        if (a.getCategoria() == "ACADEMICA"){
            Academica b = (Academica) a;
            System.out.println("Asignatura: "+b.getAsignatura());
            if(b.getSesiones().size()>0){
                System.out.println("HISTORIAL DE GESTIÓN DEL TIEMPO");
                System.out.println("Fecha"+"\t"+"Técnica Aplicada"+"\t"+"Duración (min)");
                for(SesionEnfoque s : b.getSesiones()){
                    System.out.println(s.toString());
                }
            }
        } else {
            Personal c = (Personal) a;
            System.out.println("Nombre: "+a.getNombre());
            System.out.println("Tipo: "+a.getTipo());
            System.out.println("Prioridad: "+a.getPrioridad());
            System.out.println("Estado: "+a.getEstado());
            System.out.println("Fecha Limite: "+a.getFechaVencimiento());
            System.out.println("Tiempo Estimado Total: "+a.getTiempoEstimado());
            System.out.println("Avance Actual: "+a.getProgreso());
            System.out.println("Lugar: "+ c.getLugar());
        }
        System.out.println("Presione [ENTER] para volver al menú principal...");
        sc.nextLine();
    }

    public void visualizarActividades(List <Actividad> lista){
        if(lista.size()>0){
            System.out.println("ID"+"\t"+"CATEGORIA"+"\t"+"TIPO"+"\t"+"NOMBRE"+"\t"+"VENCIMIENTO"+"\t"+"PRIORIDAD"+"\t"+"PROGRESO");
            for(Actividad a : lista){
            System.out.println(a.getId()+"\t"+a.getCategoria()+"\t"+a.getTipo()+"\t"+a.getNombre()+"\t"+a.getFechaVencimiento()+"\t"+a.getPrioridad()+"\t"+a.getProgreso());
            }
            int i=0;
            System.out.print("Seleccionar el ID de una actividad o (0 para regresar): ");
            i = sc.nextInt();
            sc.nextLine();
            if(i!=0){
                Actividad a = cA.seleccionarActividad(i, lista);
                if(a!=null){
                    detalleActividad(a);
                } else {
                    mostrarMensaje("No existe una actividad con ese ID: ");
                }
            }
        } else {
            mostrarMensaje("No hay actividades para mostrar.");
            sc.nextLine();
        }
    }

    public void registrarAvance(){
        System.out.println("--- REGISTRAR AVANCE ---");
        System.out.println("Actividades Pendientes: ");
        mostrarListaActividades(cA.getPendientes());
        if(cA.getPendientes().size()>0){
            System.out.print("Seleccione un ID de una actividad: ");
            int id = sc.nextInt();
            if(id!=0){
                Actividad a = cA.seleccionarActividad(id, cA.getPendientes());
                System.out.println("Ha seleccionado: "+a.getNombre());
                System.out.println("Avance: "+a.getProgreso());
                System.out.print("Ingrese el nuevo avance (0-100): ");
                int avance = sc.nextInt();
                sc.nextLine();
                MensajeUsuario m = cA.cambiarProgreso(id, avance);
                mostrarMensaje(m.toString());
            } 
        }
    }

    public void eliminarActividad(){
        System.out.println("--- ELIMINAR ACTIVIDAD ---");
        mostrarListaActividades(cA.getPendientes());
        if(cA.getPendientes().size()>0){
            System.out.println("Seleccione el ID de la actividad a eliminar: ");
            int id = sc.nextInt();
            sc.nextLine();
            MensajeUsuario m = cA.eliminarActividad(id);
            mostrarMensaje(m.toString());
        }
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje); 
        System.out.print("Presione [ENTER] para continuar");
        sc.nextLine();
    }
}