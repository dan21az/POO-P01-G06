package com.mycompany.app.estudiantil.controlador;

import com.mycompany.app.estudiantil.modelo.Hidratacionn.*;
import com.mycompany.app.estudiantil.modelo.sostenibilidad.Sostenibilidad;
import com.mycompany.app.estudiantil.vista.*;

/**
 * Clase ControladorPrincipal. 
 * Es el controlador principal de la aplicación FOCUS APP.
 * Se encarga de instanciar todos los controladores, modelos y vistas,
 * y gestionar el flujo de navegación del menú principal.
 */
public class ControladorPrincipal {
  // --- INSTANCIACIÓN DE VISTAS ---
    VistaMenuPrincipal vistaP = new VistaMenuPrincipal();

  // --- MÓDULO 1: GESTIÓN DE ACTIVIDADES ---
    VistaActividad vistaA = new VistaActividad();
    ControladorActividad controladorA = new ControladorActividad();

  // --- MÓDULO 2: SESIONES DE ENFOQUE (Técnicas de Manejo de Tiempo) ---
    ControladorSesionEnfoque controladorSE = new ControladorSesionEnfoque(controladorA);
    VistaSesionEnfoque vistaSE = new VistaSesionEnfoque();

  // --- MÓDULO 3: CONTROL DE HIDRATACIÓN ---
    Hidratacion modeloH= new Hidratacion();
    MenuHidratacion vistaH = new MenuHidratacion();
    HidratacionControladora controladorH = new HidratacionControladora(modeloH,vistaH);

  // --- MÓDULO 4: REGISTRO DE SOSTENIBILIDAD ---
    Sostenibilidad modeloS = new Sostenibilidad();
    VistaSostenibilidad vistaS = new VistaSostenibilidad();
    ControladorSostenibilidad controladorS = new ControladorSostenibilidad(modeloS, vistaS);

  // --- MÓDULO 5: JUEGO DE MEMORIA ---
    ControladorJuegoMemoria controladorJ = new ControladorJuegoMemoria();

    /**
     * Muestra el menú principal y gestiona la navegación entre los diferentes módulos
     * de la aplicación mediante un bucle de eventos.
     */
  public void menuPrincipal(){
    int opcion=0;
      do {
        opcion = vistaP.menuPrincipal();
        switch (opcion){
           case 1:
            controladorA.menuGestion();
            break;
          case 2:
            controladorSE.menuSesion();
            break;
          case 3:
            controladorH.iniciar();
            break;
          case 4:
            controladorS.menuSostenibilidad();;
            break;
          case 5:
            controladorJ.iniciarJuego();
          case 6:
            vistaP.salir();
            break;
          default:
            vistaP.noValido();
            break;
        }
    } while (opcion != 6);
  }

    /**
     * Inicializa la aplicación cargando algunos datos de prueba (actividades y sesiones).
     */
    public void inicializarApp(){
      // Crear actividades académicas de ejemplo
      controladorA.crearActividadAcademica("Proyecto 1","30 Nov", "ALTA", "PROYECTO", 100,70,"En curso","Proyecto de POO", "POO");
      controladorA.crearActividadAcademica("Tarea 1","3 dic", "MEDIA", "TAREA", 2,0,"No iniciada", "Tarea de Cálculo", "Cálculo");
      controladorA.crearActividadAcademica("Examen","10 dic", "MEDIA", "EXAMEN", 2,0, "No iniciada","Examen de Comunicación", "Comunicación");
      // Crear actividad personal de ejemplo
      controladorA.crearActividadPersonal("Cita Médica", "30 Nov", "ALTA", "CITAS", 1,0, "No iniciada","Chequeo rutinario", "Consultorio 12");

<<<<<<< HEAD
      // Registrar sesiones de Pomodoro históricas en el primer proyecto para simular avance
      // Se utiliza getListaActividades().get(0) para obtener la primera actividad (Proyecto 1).
=======
>>>>>>> d719903cf6984712fbe696a37151fc3c15aad508
      controladorSE.crearPomodoro("10 nov", controladorA.getListaActividades().get(0));
      controladorSE.crearPomodoro("12 nov", controladorA.getListaActividades().get(0));
    }
}

