package com.mycompany.app.estudiantil.controlador;
import com.mycompany.app.estudiantil.modelo.sostenibilidad.*;
import com.mycompany.app.estudiantil.vista.*;
import java.util.ArrayList;

public class ControladorSostenibilidad {

    private Sostenibilidad modelo;
    private VistaSostenibilidad vista;

    public ControladorSostenibilidad(Sostenibilidad modelo, VistaSostenibilidad vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void menuSostenibilidad() {
        int opcion;
        do {
            opcion = vista.mostrarMenuSostenibilidad();
            switch (opcion) {
                case 1:
                    registrarAccionesDelDia();
                    break;
                case 2:
                    vista.mostrarInformeSemanal(modelo.getRegistros());
                    break;
                case 3:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                default:
                    System.out.println("\nOpción inválida, intente nuevamente.");
            }
            if (opcion != 3) vista.pausa();
        } while (opcion != 3);
    }

    private void registrarAccionesDelDia() {
        RegistroSostenible registro = new RegistroSostenible();
        ArrayList<String> acciones = new ArrayList<>();

        int opcion;
        do {
            opcion = vista.mostrarSubmenuAcciones();
            switch (opcion) {
                case 1 -> acciones.add("Usé transporte público, bicicleta o caminé.");
                case 2 -> acciones.add("No realicé impresiones.");
                case 3 -> acciones.add("No utilicé envases descartables (usé mi termo/taza).");
                case 4 -> acciones.add("Separé y reciclé materiales (vidrio, plástico, papel).");
                case 5 -> System.out.println("\nSaliendo del registro de acciones...");
                default -> System.out.println("Opción inválida, intente nuevamente.");
            }
        } while (opcion != 5);

        for (String a : acciones) registro.agregarAccion(a);
        modelo.agregarRegistro(registro);
        vista.mostrarConfirmacionRegistro(registro);
    }
}


