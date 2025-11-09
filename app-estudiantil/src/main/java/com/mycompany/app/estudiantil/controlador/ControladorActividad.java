package com.mycompany.app.estudiantil.controlador;
import com.mycompany.app.estudiantil.vista.MensajeUsuario;
import com.mycompany.app.estudiantil.modelo.actividad.*;

import java.util.*;

public class ControladorActividad {

    private ArrayList<Actividad> listaActividades;

    public ControladorActividad(){
        this.listaActividades = new ArrayList<>();
    }

    public ArrayList<Actividad> getListaActividades() {
        return listaActividades;
    }
    
    public MensajeUsuario crearActividadPersonal(String n, String fV, String p, String t, int tE,String des,String l){
        Actividad.aumentarId();
        int id = Actividad.getContadorId();
        Personal ap = new Personal(n,fV, p, t, tE, 0, id,des, l);
        listaActividades.add(ap);
        return new MensajeUsuario("Creando Actividad", "Actividad Personal guardada");
    }

    public MensajeUsuario crearActividadAcademica(String n, String fV, String p, String t, int tE,String des, String asig){
        Actividad.aumentarId();
        int id = Actividad.getContadorId();
        Academica ac = new Academica(n,fV, p, t, tE, 0,id,des, asig);
        listaActividades.add(ac);
        return new MensajeUsuario("Creando Actividad", "Actividad Academica Guardada");
    }
    
    public MensajeUsuario cambiarProgreso(int id, int nuevoProgreso) {
        Actividad a = seleccionarActividad(id, listaActividades);
        if (nuevoProgreso < 0 || nuevoProgreso > 100){
            return new MensajeUsuario("Error", "El porcentaje debe estar entre 0 y 100.");

        } else {
        if (nuevoProgreso < 100){
            a.setProgreso(nuevoProgreso);
            a.actualizarEstado();
            return new MensajeUsuario("Progreso actualizado", "Nuevo progreso: " + nuevoProgreso + "%");
        }else
            return completarTarea(id);
        }

    }

    public MensajeUsuario completarTarea(int id){
        Actividad a = seleccionarActividad(id, listaActividades);
        a.setProgreso(100);
        a.setEstado("COMPLETADO");
        return new MensajeUsuario("Tarea completada", "La actividad '" + a.getNombre() + "' ha sido finalizada.");
}

    public MensajeUsuario eliminarActividad(int id) {
        Actividad a = seleccionarActividad(id, listaActividades);
        if (a == null)
            return new MensajeUsuario("Error", "No existe actividad con ID " + id);
        listaActividades.remove(a);
        return new MensajeUsuario("Eliminación exitosa", "Actividad '" + a.getNombre() + "' eliminada.");
    }

    public Actividad seleccionarActividad(int i, List <Actividad> lista){
        for(Actividad a: lista){
            if(a.getId() == i){
                return a;
                }
            } 
        return null;
    }

    public List<Actividad> filtrarPorCategoria(String categoria) {
        List<Actividad> filtradas = new ArrayList<Actividad>();
        List<Actividad> lista = getPendientes();
        for (Actividad a : lista) {
            if (a.getCategoria().equals(categoria)){
                filtradas.add(a);
            }
        }
        return filtradas;
    }    

    public List<Actividad> getPendientes() {
        List<Actividad> pendientes = new ArrayList<>();
        for (Actividad a : listaActividades) {
            if (a.getProgreso() < 100)
                pendientes.add(a);
        }
        return pendientes;
    }

}    

