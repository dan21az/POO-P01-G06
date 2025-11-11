package com.mycompany.app.estudiantil.controlador;

import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.Hidratacionn.*;
import com.mycompany.app.estudiantil.modelo.sostenibilidad.Sostenibilidad;
import com.mycompany.app.estudiantil.vista.*;

public class ControladorPrincipal {

    Hidratacion modeloH= new Hidratacion();
    Sostenibilidad modeloS = new Sostenibilidad();

    VistaSostenibilidad vistaS = new VistaSostenibilidad();

    MenuHidratacion vistaH = new MenuHidratacion();
    HidratacionControladora controladorH = new HidratacionControladora(modeloH,vistaH);

    ControladorActividad controladorA = new ControladorActividad();
    ControladorSesionEnfoque controladorSE = new ControladorSesionEnfoque(controladorA);
    ControladorSostenibilidad controladorS = new ControladorSostenibilidad(modeloS, vistaS);

    VistaMenuPrincipal vistaP = new VistaMenuPrincipal();
    VistaActividad vistaA = new VistaActividad(controladorA);
    VistaSesionEnfoque vistaSE = new VistaSesionEnfoque(controladorSE,controladorA);

    Scanner sc = new Scanner(System.in);

    String fecha = "Hoy";
    int opcion=0;
    public void menuPrincipal(){
    do {
        vistaP.menuPrincipal();
        opcion = sc.nextInt();
    
      switch (opcion){
           case 1:
            vistaA.menuGestion();
            break;
          case 2:
            vistaSE.menuSesion(fecha);
            break;
          case 3:
            controladorH.iniciar();
            break;
          case 4:
            //Control Sostenibilidad
            controladorS.menuSostenibilidad();;
            break;
          case 5:
            //Juego Memoria;
            vistaP.noDisponible();
            sc.nextLine();
          case 6:
            vistaP.salir();
            break;
          default:
              vistaP.noValido();
              break;
      }
    } while (opcion != 6);
    }
}

