package com.espol.application.vista.sostenibilidad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.application.R;
import com.espol.application.controlador.SostenibilidadControladora;
import com.espol.application.datos.SostenibilidadDatos;
import com.espol.application.modelo.sostenibilidad.FrecuenciaItem;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class MainSostenibilidadActivity extends AppCompatActivity {

    private SostenibilidadControladora controladora;
    private ResumenAccionAdapter adapter;

    private TextView tvDiasConAccion;
    private TextView tvDiasCompletos;
    private TextView tvRangoFechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sostenibilidad_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvTituloResumen), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });

        // Asegura carga/seed del modelo (.ser) en la primera ejecución
        SostenibilidadDatos.getInstance(this);

        controladora = SostenibilidadControladora.getInstance();

        RecyclerView rvFrecuencia = findViewById(R.id.rvFrecuencia);
        tvDiasConAccion = findViewById(R.id.tvDiasConAccion);
        tvDiasCompletos = findViewById(R.id.tvDiasCompletos);
        tvRangoFechas = findViewById(R.id.tvRangoFechas);
        MaterialButton btnRegistrar = findViewById(R.id.btnRegistrar);

        rvFrecuencia.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResumenAccionAdapter(new ArrayList<>());
        rvFrecuencia.setAdapter(adapter);

        cargarResumen();

        btnRegistrar.setOnClickListener(v -> {
            Intent i = new Intent(this, RegistroSostenibilidadActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarResumen();
    }

    private void cargarResumen() {
        String iniIso = FechaUtils.isoDiasAtras(6);
        String finIso = FechaUtils.hoyIso();
        tvRangoFechas.setText("(" + isoToBonito(iniIso) + " - " + isoToBonito(finIso) + ")");

        SostenibilidadControladora.ResumenSemanal r =
                controladora.calcularResumenUltimos7Dias(this);

        int total = 7;

        int verde = ContextCompat.getColor(this, R.color.verdePrimary);
        int naranja = ContextCompat.getColor(this, R.color.naranjaPrimary);
        int rojo = ContextCompat.getColor(this, R.color.rojoPrimary);


        ArrayList<FrecuenciaItem> filas = new ArrayList<>();
// Arma cada fila del resumen con su texto, conteo, etiqueta y color según el desempeño
        filas.add(new FrecuenciaItem(
                "Usé transporte público/bici/caminé",
                r.vecesTransporte, total,
                (r.vecesTransporte >= 5 ? "¡Gran\nMovilidad!" : "Mejorable"),
                (r.vecesTransporte >= 5 ? verde : naranja)
        ));

        filas.add(new FrecuenciaItem(
                "No realicé impresiones",
                r.vecesImpresiones, total,
                (r.vecesImpresiones == total ? "Excelente" : "Debe\nmejorar"),
                (r.vecesImpresiones == total ? verde : rojo)
        ));

        filas.add(new FrecuenciaItem(
                "No utilicé envases descartables",
                r.vecesEnvases, total,
                (r.vecesEnvases >= 4 ? "Muy bien" : "Necesita\nmejorar"),
                (r.vecesEnvases >= 4 ? verde : naranja)
        ));

        filas.add(new FrecuenciaItem(
                "Separé y reciclé materiales",
                r.vecesReciclaje, total,
                (r.vecesReciclaje >= 5 ? "Muy bien" : "Regular"),
                (r.vecesReciclaje >= 5 ? verde : naranja)
        ));

        adapter.setItems(filas); // Actualiza la lista del RecyclerView con las filas armadas
// Calcula porcentajes
        int pctConAccion = (int) Math.round((r.diasConAlMenosUnaAccion * 100.0) / total);
        int pctCompletos = (int) Math.round((r.diasCompletos * 100.0) / total);
// Muestra el total de días donde se hizo al menos una acción sostenible
        tvDiasConAccion.setText("Días con al menos 1 acción sostenible: "
                + r.diasConAlMenosUnaAccion + " de " + total + " (" + pctConAccion + "%)");
// Muestra el total de días donde se cumplieron las 4 acciones
        tvDiasCompletos.setText("Días con las 4 acciones completas: "
                + r.diasCompletos + " de " + total + " (" + pctCompletos + "%)");
    }

    private String isoToBonito(String iso) {
        String[] p = iso.split("-");
        return p[2] + "/" + p[1] + "/" + p[0];
    }
}