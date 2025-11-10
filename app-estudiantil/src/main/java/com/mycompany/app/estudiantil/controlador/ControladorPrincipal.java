package com.mycompany.app.estudiantil.controlador;

import java.util.Scanner;

import com.mycompany.app.estudiantil.modelo.Hidratacionn.*;
import com.mycompany.app.estudiantil.vista.*;

public class ControladorPrincipal {

    Hidratacion modelo= new Hidratacion();
    MenuHidratacion vista = new MenuHidratacion();
    HidratacionControladora controladora = new HidratacionControladora(modelo,vista);

    ControladorActividad cA = new ControladorActividad();
    ControladorSesionEnfoque cS = new ControladorSesionEnfoque(cA);

    VistaMenuPrincipal mP = new VistaMenuPrincipal();
    VistaActividad mA = new VistaActividad(cA);
    VistaSesionEnfoque mS = new VistaSesionEnfoque(cS,cA);

    Scanner sc = new Scanner(System.in);

    String fecha = "Hoy";
    int opcion=0;
    public void menuPrincipal(){
    do {
        mP.menuPrincipal();
        opcion = sc.nextInt();
    
      switch (opcion){
           case 1:
            mA.menuGestion();
            break;
          case 2:
            mS.menuSesion(fecha);
            break;
          case 3:
            controladora.iniciar();
            break;
          case 4:
            //Control Sostenibilidad
            mP.noDisponible();
            sc.nextLine();
            break;
          case 5:
            //Juego Memoria;
            mP.noDisponible();
            sc.nextLine();
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

