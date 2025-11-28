package com.mycompany.app.estudiantil.controlador;
import com.mycompany.app.estudiantil.modelo.Hidratacionn.Hidratacion;
import com.mycompany.app.estudiantil.vista.MenuHidratacion;

public class HidratacionControladora {

    private Hidratacion modelo;
    private MenuHidratacion vista;

    public HidratacionControladora(Hidratacion modelo, MenuHidratacion vista) {
        this.modelo = modelo;
        this.vista = vista;
    }
//Hace el bucle del menu de hidratacion y llama a los metodos necesarios segun la opcion elegida
    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1 -> {
                    int cantidad = vista.leerCantidad();
                    modelo.registrarIngesta(cantidad);
                     vista.mostrarRegistroAñadido(cantidad);
                    vista.mostrarProgresoRapido(modelo.getTotalHoy(), modelo.getMetaDiaria());
                    vista.esperarEnter();
                }
                case 2 -> {
                     int nuevaMeta = vista.leerMeta(modelo.getMetaDiaria());
                    if (vista.confirmarMeta(nuevaMeta)) {
                        modelo.establecerMeta(nuevaMeta);
                        System.out.println("Meta diaria actualizada a " + nuevaMeta + " ml con exito.");
                        vista.mostrarProgresoActualizado(modelo.getTotalHoy(), modelo.getMetaDiaria());
                        
                    } else {
                        System.out.println("Meta NO modificada.");
                    }
                    vista.esperarEnter();
                }
                case 3 -> {vista.mostrarProgreso(modelo.getTotalHoy(), modelo.getMetaDiaria());
                    vista.mostrarHistorial(modelo.getRegistrosDeHoy());
                    vista.esperarEnter();
                }
                case 4 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }//por defecto se puso que al presionar cualquier otra tecla se muestre opcion invalida
        } while (opcion != 4);
    }
}


