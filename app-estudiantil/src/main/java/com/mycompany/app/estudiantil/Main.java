package com.mycompany.app.estudiantil;
import com.mycompany.app.estudiantil.controlador.*;

public class Main {
  public static void main(String[] args) {
    ControladorPrincipal controlador = new ControladorPrincipal();

    controlador.inicializarApp();
    controlador.menuPrincipal();

  }
  
}