package com.mycompany.app.estudiantil;
import com.mycompany.app.estudiantil.controlador.HidratacionControladora;
import com.mycompany.app.estudiantil.vista.MenuHidratacion;
import com.mycompany.app.estudiantil.controlador.*;
import com.mycompany.app.estudiantil.modelo.Hidratacionn.Hidratacion;

public class Main {
  public static void main(String[] args) {
    ControladorPrincipal controlador = new ControladorPrincipal();

    controlador.menuPrincipal();
    Hidratacion modelo= new Hidratacion();
    MenuHidratacion vista = new MenuHidratacion();
    HidratacionControladora controladora = new HidratacionControladora(modelo,vista);
    controladora.iniciar();
  }
  
}