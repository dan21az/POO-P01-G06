package com.mycompany.app.estudiantil.controlador;

import java.util.Scanner;

import com.mycompany.app.estudiantil.vista.VistaActividad;
import com.mycompany.app.estudiantil.vista.VistaMenuPrincipal;
import com.mycompany.app.estudiantil.vista.VistaSesionEnfoque;

public class ControladorPrincipal {

    ControladorActividad cA = new ControladorActividad();
    ControladorSesionEnfoque cS = new ControladorSesionEnfoque(cA);

    VistaMenuPrincipal mP = new VistaMenuPrincipal();
    VistaActividad mA = new VistaActividad(cA);
    VistaSesionEnfoque mS = new VistaSesionEnfoque(cS,cA);

    Scanner scanner = new Scanner(System.in);

    String fecha = "Hoy";
    int opcion=0;
    public void menuPrincipal(){
    do {
        mP.menuPrincipal();
        opcion = scanner.nextInt();
    
      switch (opcion){
           case 1:
            mA.menuGestion();
            break;
          case 2:
            mS.menuSesion(fecha);
            break;
          case 3:
            // Control hidratacion
          case 4:
            //Control Sostenibilidad
          case 5:
            //Juego Memoria
          case 6:
            mP.salir();
            break;
          default:
              mP.noValido();
              break;
      }
    } while (opcion != 6);
    }
}

