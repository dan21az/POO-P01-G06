package com.mycompany.app.estudiantil;
import com.mycompany.app.estudiantil.controlador.*;

public class Main {
  public static void main(String[] args) {
    //Instancia el ControladorPrincipal, que se encarga de gestionar
    //todos los demás módulos (Actividades, Enfoque, etc.).
    ControladorPrincipal controlador = new ControladorPrincipal();
  // Inicializa la aplicación cargando datos de prueba o configuraciones iniciales.
    controlador.inicializarApp();
    // Inicia el bucle principal de la aplicación, mostrando el menú principal.
    controlador.menuPrincipal();

  }
  
}