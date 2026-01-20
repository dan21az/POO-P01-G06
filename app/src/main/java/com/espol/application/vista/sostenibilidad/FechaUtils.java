package com.espol.application.vista.sostenibilidad;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FechaUtils {  //Clase utilitaria para manejar y formatear fechas

    public static String hoyIso() {  //Retorna la fecha actual del sistema en formato ISO.
        return formatoIso(Calendar.getInstance());
    }
//Retorna la fecha en formato ISO correspondiente a una cantidad de días anteriores a la fecha actual
    public static String isoDiasAtras(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -dias);
        return formatoIso(cal);
    }
//Genera una fecha en formato ISO a partir de año, mes y día
    public static String isoFromYMD(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return formatoIso(cal);
    }

    private static String formatoIso(Calendar cal) {  //Convierte un objeto Calendar a texto
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(cal.getTime());
    }
}

