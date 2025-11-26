package com.mycompany.app.estudiantil.controlador;

import com.mycompany.app.estudiantil.modelo.Hidratacionn.*;
import com.mycompany.app.estudiantil.modelo.sostenibilidad.Sostenibilidad;
import com.mycompany.app.estudiantil.vista.*;

public class ControladorPrincipal {

    VistaMenuPrincipal vistaP = new VistaMenuPrincipal();

    VistaActividad vistaA = new VistaActividad();
    ControladorActividad controladorA = new ControladorActividad();

    ControladorSesionEnfoque controladorSE = new ControladorSesionEnfoque(controladorA);
    VistaSesionEnfoque vistaSE = new VistaSesionEnfoque();

    Hidratacion modeloH= new Hidratacion();
    MenuHidratacion vistaH = new MenuHidratacion();
    HidratacionControladora controladorH = new HidratacionControladora(modeloH,vistaH);

    Sostenibilidad modeloS = new Sostenibilidad();
    VistaSostenibilidad vistaS = new VistaSostenibilidad();
    ControladorSostenibilidad controladorS = new ControladorSostenibilidad(modeloS, vistaS);

    ControladorJuegoMemoria controladorJ = new ControladorJuegoMemoria();

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

    public void inicializarApp(){
      controladorA.crearActividadAcademica("Proyecto 1","30 Nov", "ALTA", "PROYECTO", 100,70, "Proyecto de POO", "POO");
      controladorA.crearActividadAcademica("Tarea 1","3 dic", "MEDIA", "TAREA", 2,0, "Tarea de Cálculo", "Cálculo");
      controladorA.crearActividadAcademica("Examen","10 dic", "MEDIA", "EXAMEN", 2,0, "Examen de Comunicación", "Comunicación");
      controladorA.crearActividadPersonal("Cita Médica", "30 Nov", "ALTA", "CITAS", 1,0, "Chequeo rutinario", "Consultorio 12");

      controladorSE.crearPomodoro("10 Nov", controladorA.getListaActividades().get(0));
      controladorSE.crearPomodoro("12 Nov", controladorA.getListaActividades().get(0));
    }
}

