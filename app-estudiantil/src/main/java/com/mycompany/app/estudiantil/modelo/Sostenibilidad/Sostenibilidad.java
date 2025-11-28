package com.mycompany.app.estudiantil.modelo.sostenibilidad;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sostenibilidad {
    private ArrayList<RegistroSostenible> registros;

    public Sostenibilidad() {
        registros = new ArrayList<>();

        //Ayer
        RegistroSostenible dia1 = new RegistroSostenible(LocalDate.now().minusDays(1));
        dia1.agregarAccion("Usé transporte público, bicicleta o caminé.");
        dia1.agregarAccion("Separé y reciclé materiales (vidrio, plástico, papel).");
        registros.add(dia1);

        //hace dos días
        RegistroSostenible dia2 = new RegistroSostenible(LocalDate.now().minusDays(2));
        dia2.agregarAccion("Transporte sostenible");
        dia2.agregarAccion("No utilicé envases descartables (usé mi termo/taza).");
        registros.add(dia2);
    }
    

    public void agregarRegistro(RegistroSostenible r) {
        registros.add(r);
    }

    public ArrayList<RegistroSostenible> getRegistros() {
        return registros;
    }
}

