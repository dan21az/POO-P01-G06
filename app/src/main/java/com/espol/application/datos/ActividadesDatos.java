package com.espol.application.datos;


import android.content.Context;
import android.util.Log;

import com.espol.application.modelo.actividad.Academica;
import com.espol.application.modelo.actividad.Actividad;
import com.espol.application.modelo.actividad.Personal;
import com.espol.application.modelo.sesionenfoque.Pomodoro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

// Clase para guardar datos y manejarlos
public class ActividadesDatos implements Serializable {

    private static final ActividadesDatos INSTANCIA = new ActividadesDatos();
    private ArrayList<Actividad> listaActividades;
    private static final String NOMBRE_ARCHIVO = "actividades.ser";
    private File directorioArchivos;
    private ActividadesDatos() {
        listaActividades = new ArrayList<>();
    }

    //Obtener una misma lista y funciones en todas las pantallas de Actividad
    public static ActividadesDatos getInstancia() {
        return INSTANCIA;
    }

    public List<Actividad> getListaActividades() {
        return listaActividades;
    }


    public void inicializar(Context context) {
        this.directorioArchivos = context.getFilesDir();
        cargarDatos();
    }

    //Cargar los datos de prueba o los archivos
    public void cargarDatos() {
        File f = new File(directorioArchivos, NOMBRE_ARCHIVO);
        if (f.exists()) {
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {

                // Leer la lista completa desde el archivo
                listaActividades = (ArrayList<Actividad>) is.readObject();

                // Restablacer el contador de actividades según las presentes en el archivo
                gestionarContadorId();

            } catch (Exception e) {
                Log.e("Repositorio", "Error al cargar actividades serializadas: " + e.getMessage());

            }
        } else {
            // Si el archivo no existe o hubo un error de lectura, se cargar datos de prueba
            cargarDatosPrueba();
        }

    }

    private void cargarDatosPrueba() {

        listaActividades.clear();

        listaActividades.add(new Personal("Cita Médica", "PERSONAL", "20/01/2026 15:30", "Media", "Citas",
                1, 0, 1, "No iniciado", "Chequeo de rutina ", "Consultorio del Dr. Pérez"));

        listaActividades.add(new Academica("Proyecto POO", "ACADEMICA", "30/01/2026 10:00", "Alta", "Proyecto",
                300, 70, 2, "En curso", "Implementar clases en el paquete modelo", "POO"));

        listaActividades.add(new Academica("Tarea de Cálculo", "ACADEMICA", "19/01/2026 23:59", "Baja", "Tareas",
                4, 0, 3, "No iniciado", "Ejercicios del 120 al 140 del libro de Cálculo", "Cálculo"));

        listaActividades.add(new Academica("Exámen Final", "ACADEMICA", "23/01/2026 08:30", "Media", "Examen",
                8, 100, 4, "Completado", "Repasar los temas del segundo parcial ", "Estadística"));

        Academica proyecto = (Academica) listaActividades.get(1);
        proyecto.añadirSesion(new Pomodoro("2026-01-05 10:00", proyecto, 4));
        proyecto.añadirSesion(new Pomodoro("2026-01-10 15:00", proyecto, 2));

        gestionarContadorId();
    }

    private void gestionarContadorId() {
        int maxId = 0;

        if (listaActividades.isEmpty()) {
            Actividad.setContadorId(0); // Si la lista está vacía, reiniciar el contador
            return;
        }

        // Iterar sobre todos los elementos para encontrar el ID más alto.
        for (Actividad a : listaActividades) {
            if (a.getId() > maxId) {
                maxId = a.getId();
            }
        }

        // Establecer el contador estático al ID más alto encontrado.
        Actividad.setContadorId(maxId);
        Log.i("Repositorio", "Contador de ID actualizado al valor: " + maxId);
    }

    public boolean guardarActividades() {
        if (directorioArchivos == null) return false;

        File f = new File(directorioArchivos, NOMBRE_ARCHIVO);

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(listaActividades);
            return true;
        } catch (Exception e) {
            Log.e("Repositorio", "Error al guardar actividades: " + e.getMessage());
            return false;
        }
    }

    public void agregarActividad(Actividad nuevaActividad) {
        listaActividades.add(nuevaActividad);
        guardarActividades(); //Guardar en el archivo
    }

    public boolean actualizarProgreso(long idActividad, int nuevoAvance) {

        Actividad actividad = buscarActividadPorId(idActividad);
            // Comprobamos si el ID de la actividad coincide con el ID buscado
            if (actividad.getId() == idActividad) {

                actividad.actualizarAvance(nuevoAvance);
                guardarActividades(); //Actualizar el archivo
                return true;
            } else {
                return false;
            }

        }

    public boolean eliminarActividad(long idActividad) {
        for (int i = 0; i < listaActividades.size(); i++) {
            if (listaActividades.get(i).getId() == idActividad) {
                listaActividades.remove(i);
                guardarActividades();

                return true;
            }
        }
        return false;
    }

    public Actividad buscarActividadPorId(long id) {

        for (Actividad actividad : listaActividades) {
            if (actividad.getId() == id) {
                return actividad;
            }
        }
        return null;
    }

    public List<Actividad> filtrarPorTipo(String filtro) {

        // Si el filtro es "Todos", devuelve la lista completa.
        if ("Todos".equals(filtro)) {
            // Devolver una copia para evitar modificaciones accidentales
            return new ArrayList<>(listaActividades);
        }

        // Si no es "todosS", aplica se filtra
        ArrayList<Actividad> listaFiltrada = new ArrayList<>();

        String filtroUpper = filtro.toUpperCase();

        for (Actividad actividad : listaActividades) {
            // Obtener el tipo de la actividad
            String tipoActividad = actividad.getTipo().toUpperCase();

            // Solo añade la actividad si su tipo coincide exactamente con el filtro.
            if (tipoActividad.equals(filtroUpper)) {
                listaFiltrada.add(actividad);
            }
        }

        return listaFiltrada;
    }

    public List<Actividad> filtrarNoVencidas(List<Actividad> lista) {
        List<Actividad> resultado = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date hoy = new Date();

        for (Actividad actividad : lista) {
            try {
                Date vencimiento = sdf.parse(actividad.getFechaVencimiento());
                if (!vencimiento.before(hoy)) {
                    resultado.add(actividad);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public List<Actividad> ordenarLista(List<Actividad> lista, String criterio) {

        List<Actividad> copia = new ArrayList<>(lista);

        switch (criterio) {
            case "Nombre A-Z":
                Collections.sort(copia, Comparator.comparing(Actividad::getNombre, String.CASE_INSENSITIVE_ORDER));
                break;

            case "Fecha (desc)":
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Collections.sort(copia, (a1, a2) -> {
                    try {
                        Date d1 = sdf.parse(a1.getFechaVencimiento());
                        Date d2 = sdf.parse(a2.getFechaVencimiento());
                        return d2.compareTo(d1); // Descendente
                    } catch (Exception e) {
                        return 0;
                    }
                });
                break;

            case "Avance (desc)":
                Collections.sort(copia, (a1, a2) -> Integer.compare(a2.getProgreso(), a1.getProgreso()));
                break;
        }

        return copia;
    }

    public boolean actualizarActividad(Actividad actividadActualizada) {
        for (int i = 0; i < listaActividades.size(); i++) {
            if (listaActividades.get(i).getId() == actividadActualizada.getId()) {

                // Reemplazar la versión anterior por la versión actualizada
                listaActividades.set(i, actividadActualizada);

                guardarActividades();

                return true;
            }
        }
        return false;
    }

    public void completarTodasLasActividades() {
        for (Actividad actividad : listaActividades) {
            actividad.setProgreso(100);
            actividad.setEstado("Completado");
        }
        guardarActividades();
    }

    public int eliminarActividadesCompletadas() {
        int sizeBefore = listaActividades.size();

        // Remover según la condicon (activdades con progreso mayor a 100
        listaActividades.removeIf(actividad -> actividad.getProgreso() >= 100);

        int removedCount = sizeBefore - listaActividades.size();
        if (removedCount > 0) {
            gestionarContadorId();
            guardarActividades();
        }
        return removedCount;
    }

    public void eliminarTodasLasActividades() {
        listaActividades.clear();
        gestionarContadorId(); // Reiniciar el contador a 0
        guardarActividades();
    }

}