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

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1 -> {
                    int cantidad = vista.leerCantidad();
                    modelo.registrarIngesta(cantidad);
                    vista.mostrarProgreso(modelo.getTotalHoy(), modelo.getMetaDiaria());
                    vista.esperarEnter();
                }
                case 2 -> {
                    int meta = vista.leerMeta();
                    modelo.establecerMeta(meta);
                    vista.mostrarProgreso(modelo.getTotalHoy(), modelo.getMetaDiaria());
                    vista.esperarEnter();
                }
                case 3 -> {vista.mostrarProgreso(modelo.getTotalHoy(), modelo.getMetaDiaria());
                    vista.esperarEnter();
                }
                case 4 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }
}


