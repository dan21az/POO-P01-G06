package com.espol.application.vistas.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.application.R;
import com.espol.application.datos.ActividadesDatos;
import com.espol.application.modelos.actividad.Academica;
import com.espol.application.modelos.actividad.Actividad;
import com.espol.application.modelos.actividad.Personal;
import com.espol.application.modelos.sesionenfoque.SesionEnfoque;
import com.espol.application.vistas.sesionEnfoque.HistorialSesionAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetalleActividad extends AppCompatActivity {

    public static final String EXTRA_ACTIVIDAD_ID = "actividad_id";

    private TextView tvTitulo, tvNombre, tvTipo, tvAsignatura, tvPrioridad, tvEstado,
            tvFechaLimite, tvTiempoEstimado, tvAvanceActual, tvDescripcion,tvLugar;
    private CardView cardHistorialTiempo;
    private RecyclerView recyclerViewHistorial;
    private HistorialSesionAdapter historialAdapter;
    private long actividadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle_actividad);
        inicializarVistas();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tv_detalle_titulo), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });
        EdgeToEdge.enable(this);

        // Obtener la actividad
        actividadId = getIntent().getLongExtra(EXTRA_ACTIVIDAD_ID, -1);
        Actividad actividad = ActividadesDatos.getInstancia().buscarActividadPorId(actividadId);
        mostrarDetalles(actividad);

        // Configurar botones
        Button btnVolver = findViewById(R.id.btn_volver_listado);
        btnVolver.setOnClickListener(v -> finish());

        Button btnEditar = findViewById(R.id.btn_editar_actividad);
        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalleActividad.this, EditarActividad.class);
            intent.putExtra("actividad_id", actividadId);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosActividad();
    }

    private void cargarDatosActividad() {
        Actividad actividad = ActividadesDatos.getInstancia().buscarActividadPorId(actividadId);
        if (actividad != null) {
            mostrarDetalles(actividad);
        } else {
            Toast.makeText(this, "La actividad ya no existe", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void inicializarVistas() {

        // Inicializar textos y botones
        tvTitulo = findViewById(R.id.tv_detalle_titulo);
        tvNombre = findViewById(R.id.detalle_tv_nombre);
        tvTipo = findViewById(R.id.detalle_tv_tipo);
        tvAsignatura = findViewById(R.id.detalle_tv_asignatura);
        tvPrioridad = findViewById(R.id.detalle_tv_prioridad);
        tvEstado = findViewById(R.id.detalle_tv_estado);
        tvFechaLimite = findViewById(R.id.detalle_tv_fecha_limite);
        tvTiempoEstimado = findViewById(R.id.detalle_tv_tiempo_estimado);
        tvAvanceActual = findViewById(R.id.detalle_tv_avance_actual);
        tvDescripcion = findViewById(R.id.detalle_tv_descripcion);
        tvLugar = findViewById(R.id.detalle_tv_lugar);

        // Inicialización de las sesiones de enfoque
        cardHistorialTiempo = findViewById(R.id.card_historial_tiempo);
        recyclerViewHistorial = findViewById(R.id.recycler_view_historial_tiempo);
        recyclerViewHistorial.setLayoutManager(new LinearLayoutManager(this));
        historialAdapter = new HistorialSesionAdapter(new ArrayList<>());
        recyclerViewHistorial.setAdapter(historialAdapter);

    }

    private void mostrarDetalles(@NonNull Actividad actividad) {

        tvTitulo.setText("Detalles de la actividad (ID " + actividad.getId() + ")");

        int mode =  0;
        tvNombre.setText(Html.fromHtml("<b>Nombre:</b> " + actividad.getNombre(),mode));
        tvTipo.setText(Html.fromHtml("<b>Tipo:</b> " + actividad.getTipo(),mode));
        tvPrioridad.setText(Html.fromHtml("<b>Prioridad:</b> " + actividad.getPrioridad(),mode));
        tvEstado.setText(Html.fromHtml("<b>Estado:</b> " + actividad.getEstado(),mode));
        tvFechaLimite.setText(Html.fromHtml("<b>Fecha Límite:</b> " + actividad.getFechaVencimiento(),mode));
        tvAvanceActual.setText(Html.fromHtml("<b>Avance Actual:</b> " + actividad.getProgreso() + "%",mode));
        tvDescripcion.setText(Html.fromHtml("<b>Descripción:</b> " + actividad.getDescripcion(),mode));
        tvTiempoEstimado.setText(Html.fromHtml("<b>Tiempo Estimado Total:</b> " + getDuracionFormateada(actividad.getTiempoEstimado()),mode));

        // Ocultar las secciones de actividades segun la clase
        tvAsignatura.setVisibility(View.GONE);
        tvLugar.setVisibility(View.GONE);
        cardHistorialTiempo.setVisibility(View.GONE);

        if (actividad instanceof Academica) { //Actividad Académica
            Academica academica = (Academica) actividad;
            tvAsignatura.setVisibility(View.VISIBLE);
            tvAsignatura.setText(Html.fromHtml("<b>Asignatura:</b> " + academica.getAsignatura()));

            List<SesionEnfoque> historial = academica.getSesiones();
            if (historial != null && !historial.isEmpty()) { //Si existe alguna sesion de enfoque
                cardHistorialTiempo.setVisibility(View.VISIBLE);
                historialAdapter.setSesiones(historial);
            } else {
                cardHistorialTiempo.setVisibility(View.GONE);
            }

        } else if (actividad instanceof Personal) { // Actividad Personal
            Personal personal = (Personal) actividad;
            tvLugar.setVisibility(View.VISIBLE);
            tvLugar.setText(Html.fromHtml("<b>Lugar:</b> " + personal.getLugar()));
        }
    }

    public String getDuracionFormateada(float duracionEnHoras) {
        // Usamos Math.round para evitar errores de precisión
        int minutosTotales = Math.round(duracionEnHoras * 60);

        if (minutosTotales < 60) {
            // Menor a 60 minutos
            return minutosTotales + " minutos";
        } else {
            int horas = minutosTotales / 60;
            int minutosRestantes = minutosTotales % 60;

            if (minutosRestantes == 0) {
                // Mayor a 60 y es una hora exacta
                return horas + (horas == 1 ? " hora" : " horas");
            } else {
                // Mayor a 60 y no es exacta
                return horas + (horas == 1 ? " hora y " : " horas y ") + minutosRestantes + " minutos";
            }
        }
    }

}
