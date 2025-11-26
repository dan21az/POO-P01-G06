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
        boolean b =true;
        do {
            opcion = vista.mostrarMenuSostenibilidad();
            switch (opcion) {
                case 1:
                    registrarAccionesDelDia();
                    return;
                case 2:
                    System.out.println("\nVolviendo al menú principal...");
                    return;
                default:
                    System.out.println("\nOpción inválida, intente nuevamente.");
            }
        } while (b);
    }

    private void registrarAccionesDelDia() {
        RegistroSostenible registro = new RegistroSostenible();
        ArrayList<String> acciones = new ArrayList<>();

        ArrayList<Integer> opciones;
        boolean bandera= true; 
        while (bandera){
            opciones = vista.mostrarSubmenuAcciones();
            boolean todasValidas = true;
            for (int op : opciones){
                if (op < 1 || op > 4){
                    todasValidas = false;
                    break;
                }
            }
            for (int opcion : opciones){
                switch (opcion) {
                    case 1 -> acciones.add("Usé transporte público, bicicleta o caminé.");
                    case 2 -> acciones.add("No realicé impresiones.");
                    case 3 -> acciones.add("No utilicé envases descartables (usé mi termo/taza).");
                    case 4 -> acciones.add("Separé y reciclé materiales (vidrio, plástico, papel).");
                    default -> System.out.println("Opción inválida, intente nuevamente.");
                }
            }
            if (todasValidas){
                bandera=false;
            }
        }
        for (String a : acciones) registro.agregarAccion(a);
        modelo.agregarRegistro(registro);
        vista.mostrarConfirmacionRegistro(registro);
        vista.pausarParaInforme();
        vista.mostrarInformeSemanal(modelo.getRegistros());
        vista.pausarParaSalir();
        return;
    }
    
}

