package com.mycompany.app.estudiantil.vista;
import java.util.Scanner;

import com.mycompany.app.estudiantil.controlador.*;
import com.mycompany.app.estudiantil.modelo.actividad.*;
import com.mycompany.app.estudiantil.modelo.sesionenfoque.SesionEnfoque;

public class VistaSesionEnfoque {
    private ControladorSesionEnfoque c;
    private VistaActividad v;
    private ControladorActividad ca;
    private Scanner sc;

    public VistaSesionEnfoque(ControladorSesionEnfoque c, ControladorActividad ca){
        this.c = c;
        this.ca = ca;
        this.v = new VistaActividad(ca);
        this.sc = new Scanner(System.in);

    }

    public void menuSesion(String fecha){
        int opcion = 0;
        do {
            System.out.println("--- T É C N I C A S  D E  E N F O Q U E ---");
            System.out.println("1. Iniciar Pomodoro (25 min Trabajo / 5 min Descanso)");
            System.out.println("2. Iniciar Deep Work (Sesión Larga de 90 min)");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                iniciarPomodoro(fecha);
                break;
            case 2:
                iniciarDeepWork(fecha);
                break;
            case 3:
                System.out.println("Volviendo al men");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        } while (opcion != 3);
    }

    public void iniciarPomodoro(String fecha){
        System.out.println("--- INICIAR POMODORO ---");
        System.out.println("Seleccione la Actividad a trabajar:");
        v.mostrarListaActividades(ca.filtrarPorCategoria("ACADEMICA"));
        if(ca.filtrarPorCategoria("ACADEMICA").size()!= 0){
        System.out.print("Ingrese ID de la actividad (o 0 para salir): ");
        int id = sc.nextInt();
        if(id !=0){
            Actividad a = ca.seleccionarActividad(id, ca.filtrarPorCategoria("ACADEMICA"));
            Academica b = (Academica) a;
            System.out.print("Ingrese el número de ciclos: ");
            int i = sc.nextInt();
            sc.nextLine();
            MensajeUsuario m = c.iniciarPomodoro(fecha, b, i);
            if (m!=null){
                mostrarMensaje(m.toString());
            }
            finalizarSesion();
        }}
    }

    public void iniciarDeepWork(String fecha){
        System.out.println("--- INICIAR DeepWork ---");
        System.out.println("Seleccione la Actividad a trabajar: ");
        v.mostrarListaActividades(ca.filtrarPorCategoria("ACADEMICA"));
        if(ca.filtrarPorCategoria("ACADEMICA").size()!= 0){
            System.out.print("Ingrese ID de la actividad (o 0 para salir): ");
            int id = sc.nextInt();
            if(id!=0){
                Actividad a = ca.seleccionarActividad(id, ca.getListaActividades());
                Academica b = (Academica) a;
                MensajeUsuario m = c.iniciarDeepWork(fecha, b);
                if (m!=null){
                mostrarMensaje(m.toString());
                }
            finalizarSesion();
            }
        }

    }

    public void finalizarSesion(){
        System.out.println("Presione ENTER para finalizar la sesión... ");
        sc.nextLine();
        SesionEnfoque i = c.getSesionesEnfoque().get(c.getSesionesEnfoque().size()-1);
        c.finalizarSesion(i);
        MensajeUsuario m = new MensajeUsuario("Finalizado", "Se ha guardado su Progreso");
        if (m!=null){
            mostrarMensaje(m.toString());
          }
        }

    public void mostrarDetalles(SesionEnfoque s){
        System.out.println(s.getFecha()+"\t"+s.getTipoTecnica()+"\t"+s.getContadorTiempo());
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);   
    }

}

