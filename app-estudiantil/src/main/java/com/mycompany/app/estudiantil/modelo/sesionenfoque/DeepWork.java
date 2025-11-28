package com.mycompany.app.estudiantil.modelo.sesionenfoque;

import com.mycompany.app.estudiantil.modelo.actividad.*;

/**
 * Clase DeepWork. 
 * Extiende SesionEnfoque para implementar la técnica de Trabajo Profundo (Deep Work).
 * Esta técnica se caracteriza por largos periodos de concentración ininterrumpida
 * con el objetivo de maximizar la calidad y cantidad de producción en un estado cognitivo óptimo.
 */
public class DeepWork extends SesionEnfoque{

    /**
     * Constructor de la clase DeepWork.
     * Llama al constructor de la superclase (SesionEnfoque) con los valores típicos de Deep Work.
     * Se usa 90 minutos de enfoque.
     * @param fecha Fecha de la sesión.
     * @param actividad Actividad asociada.
     */
    public DeepWork(String fecha, Actividad actividad){
        // Valores de Deep Work: típicamente un bloque largo (90 min de enfoque)
        super(90,5,"DeepWork",fecha, actividad);
    }

}
