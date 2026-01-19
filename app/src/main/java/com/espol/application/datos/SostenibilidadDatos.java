package com.espol.application.datos;

import android.content.Context;

import com.espol.application.modelos.sostenibilidad.Sostenibilidad;
import com.espol.application.vistas.sostenibilidad.FechaUtils;
import com.espol.application.controlador.SostenibilidadControladora;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SostenibilidadDatos {

    private static SostenibilidadDatos instancia;
    private static final String ARCHIVO = "sostenibilidad.ser";

    private Sostenibilidad modelo;
    private Context context;

    private SostenibilidadDatos(Context context) {
        this.context = context.getApplicationContext();
        cargarModelo();
    }

    // Obtiene la única instancia de la clase
    public static SostenibilidadDatos getInstance(Context context) {
        if (instancia == null) {
            instancia = new SostenibilidadDatos(context);
        }
        return instancia;
    }

    private void cargarModelo() {
        try {
            FileInputStream fis = context.openFileInput(ARCHIVO);
            ObjectInputStream ois = new ObjectInputStream(fis);
            modelo = (Sostenibilidad) ois.readObject();
            ois.close();
        } catch (Exception e) {
            // Primera ejecución: no existe el archivo
            modelo = new Sostenibilidad();
            cargarDatosIniciales();
            guardarModelo();
        }
    }

    //Guarda el modelo completo
    private void guardarModelo() {
        try {
            FileOutputStream fos = context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(modelo);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     //Carga datos de ejemplo SOLO la primera vez que se ejecuta el módulo
    private void cargarDatosIniciales() {
        int year = 2026;

        String f17 = FechaUtils.isoFromYMD(year, 1, 17);
        String f18 = FechaUtils.isoFromYMD(year, 1, 18);

        ArrayList<String> acc17 = new ArrayList<>();
        acc17.add(SostenibilidadControladora.A2_IMPRESIONES);
        acc17.add(SostenibilidadControladora.A4_RECICLAJE);

        ArrayList<String> acc18 = new ArrayList<>();
        acc18.add(SostenibilidadControladora.A1_TRANSPORTE);
        acc18.add(SostenibilidadControladora.A2_IMPRESIONES);
        acc18.add(SostenibilidadControladora.A3_ENVASES);

        modelo.guardarRegistro(f17, acc17);
        modelo.guardarRegistro(f18, acc18);
    }

    // Guarda o actualiza las acciones de una fecha
    public void guardarAcciones(String fechaIso, ArrayList<String> acciones) {
        modelo.guardarRegistro(fechaIso, acciones);
        guardarModelo();
    }

    // Obtiene las acciones registradas para una fecha
    public ArrayList<String> cargarAcciones(String fechaIso) {
        return modelo.obtenerAcciones(fechaIso);
    }

    // Devuelve el modelo completo (por si se necesita para cálculos)
    public Sostenibilidad getModelo() {
        return modelo;
    }
}

