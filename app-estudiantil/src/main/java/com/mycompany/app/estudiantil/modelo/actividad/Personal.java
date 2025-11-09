package com.mycompany.app.estudiantil.modelo.actividad;

public class Personal extends Actividad{
    private String lugar;

    public Personal(String n, String fV, String p, String t, int tE, int pr, int id, String des, String l){
        super(n, "PERSONAL", fV, p, t, tE, pr, id, des);
        this.lugar = l;
    }

    public String getLugar() {
        return lugar;
    }

}
