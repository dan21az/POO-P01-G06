package com.espol.application.controlador;

import android.content.Context;

import com.espol.application.datos.SostenibilidadDatos;
import com.espol.application.vista.sostenibilidad.FechaUtils;

import java.util.ArrayList;

public class SostenibilidadControladora {

    private static SostenibilidadControladora instance;

    public static SostenibilidadControladora getInstance() {
        if (instance == null) instance = new SostenibilidadControladora();
        return instance;
    }

    // Acciones
    public static final String A1_TRANSPORTE  = "Usé transporte público, bicicleta o caminé.";
    public static final String A2_IMPRESIONES = "No realicé impresiones.";
    public static final String A3_ENVASES     = "No utilicé envases descartables (usé mi termo/taza).";
    public static final String A4_RECICLAJE   = "Separé y reciclé materiales (vidrio, plástico, papel).";

    private SostenibilidadControladora() {}

    public ArrayList<String> getAccionesDisponibles() {
        ArrayList<String> acciones = new ArrayList<>();
        acciones.add(A1_TRANSPORTE);
        acciones.add(A2_IMPRESIONES);
        acciones.add(A3_ENVASES);
        acciones.add(A4_RECICLAJE);
        return acciones;
    }

    //Calcula el resumen de los últimos 7 días leyendo desde SostenibilidadDatos (.ser)
    public ResumenSemanal calcularResumenUltimos7Dias(Context ctx) {
        int total = 7;

        int tPublico = 0, impresiones = 0, envases = 0, reciclaje = 0;
        int diasConAlMenosUna = 0;
        int diasCompletos = 0;

        SostenibilidadDatos datos = SostenibilidadDatos.getInstance(ctx);

        for (int i = 0; i < total; i++) {
            String fecha = FechaUtils.isoDiasAtras(i);
            ArrayList<String> acc = datos.cargarAcciones(fecha);

            int countAcc = acc.size();
            if (countAcc > 0) diasConAlMenosUna++;
            if (countAcc == 4) diasCompletos++;

            for (String a : acc) {
                String s = a.toLowerCase();

                if (s.contains("transporte") || s.contains("bic") || s.contains("camin")) {
                    tPublico++;
                } else if (s.contains("impresion")) {
                    impresiones++;
                } else if (s.contains("envase") || s.contains("termo") || s.contains("taza")) {
                    envases++;
                } else if (s.contains("recicl")) {
                    reciclaje++;
                }
            }
        }

        return new ResumenSemanal(
                total,
                tPublico, impresiones, envases, reciclaje,
                diasConAlMenosUna,
                diasCompletos
        );
    }

    public static class ResumenSemanal {
        public final int totalDiasConRegistro;
        public final int vecesTransporte;
        public final int vecesImpresiones;
        public final int vecesEnvases;
        public final int vecesReciclaje;
        public final int diasConAlMenosUnaAccion;
        public final int diasCompletos;

        public ResumenSemanal(
                int totalDiasConRegistro,
                int vecesTransporte,
                int vecesImpresiones,
                int vecesEnvases,
                int vecesReciclaje,
                int diasConAlMenosUnaAccion,
                int diasCompletos
        ) {
            this.totalDiasConRegistro = totalDiasConRegistro;
            this.vecesTransporte = vecesTransporte;
            this.vecesImpresiones = vecesImpresiones;
            this.vecesEnvases = vecesEnvases;
            this.vecesReciclaje = vecesReciclaje;
            this.diasConAlMenosUnaAccion = diasConAlMenosUnaAccion;
            this.diasCompletos = diasCompletos;
        }
    }
}



