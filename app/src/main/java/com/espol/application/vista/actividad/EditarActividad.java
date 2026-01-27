package com.espol.application.vista.actividad;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.application.R;
import com.espol.application.datos.ActividadesDatos;
import com.espol.application.modelo.actividad.Academica;
import com.espol.application.modelo.actividad.Actividad;
import com.espol.application.modelo.actividad.Personal;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditarActividad extends AppCompatActivity {

    private Spinner spinnerCategoria, spinnerPrioridad, spinnerTipo;
    private TextInputLayout tilNombre, tilDescripcion, tilTiempoEstimado, tilFechaHora, tilAsignatura, tilLugar;
    private TextInputEditText etNombre, etDescripcion, etTiempoEstimado, etAsignatura, etLugar;
    private AutoCompleteTextView etFechaHora;
    private Button btnGuardar, btnCancelar;
    private TextView tvTitulo, tvTipo, tvAsignatura, tvLugar;

    private long actividadId;
    private Actividad actividadAEditar;
    private Calendar selectedDateTimeCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);



        // Reutilizamos el layout de ingresar actividad
        setContentView(R.layout.activity_actividad_ingresar_actividad);

        inicializarVistas();
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tv_titulo), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });

        configurarSpinners();

        // Obtener ID de la actividad a editar
        actividadId = getIntent().getLongExtra("actividad_id", -1);
        actividadAEditar = ActividadesDatos.getInstancia().buscarActividadPorId(actividadId);

        if (actividadAEditar != null) {
            cargarDatosEnInterfaz();
        } else {
            Toast.makeText(this, "Error: Actividad no encontrada", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Configurar listeners
        etFechaHora.setOnClickListener(v -> selectorFecha());
        btnGuardar.setOnClickListener(v -> guardarCambios());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void inicializarVistas() {
        tvTitulo = findViewById(R.id.tv_titulo);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnCancelar = findViewById(R.id.btn_cancelar);
        tilNombre = findViewById(R.id.til_nombre);
        tilDescripcion = findViewById(R.id.til_descripcion);
        tilFechaHora = findViewById(R.id.til_fecha_hora);
        tilTiempoEstimado = findViewById(R.id.til_tiempo_estimado);
        tilAsignatura = findViewById(R.id.til_asignatura);
        tilLugar = findViewById(R.id.til_lugar);

        spinnerCategoria = findViewById(R.id.spinner_categoria);
        spinnerPrioridad = findViewById(R.id.spinner_prioridad);
        spinnerTipo = findViewById(R.id.spinner_tipo);

        etNombre = (TextInputEditText) tilNombre.getEditText();
        etDescripcion = (TextInputEditText) tilDescripcion.getEditText();
        etTiempoEstimado = (TextInputEditText) tilTiempoEstimado.getEditText();
        etFechaHora = (AutoCompleteTextView) tilFechaHora.getEditText();
        etAsignatura = (TextInputEditText) tilAsignatura.getEditText();
        etLugar = (TextInputEditText) tilLugar.getEditText();

        tvTipo = findViewById(R.id.tv_tipo);
        tvAsignatura = findViewById(R.id.tv_asignatura);
        tvLugar = findViewById(R.id.tv_lugar);
    }

    private void configurarSpinners() {
        // Categorías
        String[] categorias = {"Personal", "Académica"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(catAdapter);

        // Prioridad
        String[] prioridades = {"Alta", "Media", "Baja"};
        ArrayAdapter<String> prioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, prioridades);
        spinnerPrioridad.setAdapter(prioAdapter);

        // Listener para actualizar tipos según categoría
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarInterfazPorCategoria(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void cargarDatosEnInterfaz() {
        tvTitulo.setText("Editar Actividad (ID: " + actividadId + ")");
        btnGuardar.setText("Actualizar");

        etNombre.setText(actividadAEditar.getNombre());
        etDescripcion.setText(actividadAEditar.getDescripcion());
        etFechaHora.setText(actividadAEditar.getFechaVencimiento());

        // Tiempo estimado de horas a minutos
        int minutos = Math.round(actividadAEditar.getTiempoEstimado() * 60);
        etTiempoEstimado.setText(String.valueOf(minutos));

        seleccionarEnSpinner(spinnerPrioridad, actividadAEditar.getPrioridad());

        // Manejo por tipo de clase
        if (actividadAEditar instanceof Academica) {
            seleccionarEnSpinner(spinnerCategoria, "Académica");
            actualizarInterfazPorCategoria("Académica");
            etAsignatura.setText(((Academica) actividadAEditar).getAsignatura());
            seleccionarEnSpinner(spinnerTipo, actividadAEditar.getTipo());
        } else if (actividadAEditar instanceof Personal) {
            seleccionarEnSpinner(spinnerCategoria, "Personal");
            actualizarInterfazPorCategoria("Personal");
            etLugar.setText(((Personal) actividadAEditar).getLugar());
            seleccionarEnSpinner(spinnerTipo, actividadAEditar.getTipo());
        }

        // DESHABILITAR categoría para evitar conflictos de modelo
        spinnerCategoria.setEnabled(false);
    }

    private void guardarCambios() {
        // Validaciones básicas
        if (etNombre.getText().toString().trim().isEmpty() ||
                etTiempoEstimado.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Nombre y Tiempo son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float tiempoHoras = Float.parseFloat(etTiempoEstimado.getText().toString()) / 60;
            Actividad actualizada;

            if (actividadAEditar instanceof Academica) {
                actualizada = new Academica(
                        etNombre.getText().toString(), "ACADEMICA", etFechaHora.getText().toString(),
                        spinnerPrioridad.getSelectedItem().toString(), spinnerTipo.getSelectedItem().toString(),
                        tiempoHoras, actividadAEditar.getProgreso(), (int) actividadId,
                        actividadAEditar.getEstado(), etDescripcion.getText().toString(), etAsignatura.getText().toString()
                );
            } else {
                actualizada = new Personal(
                        etNombre.getText().toString(), "PERSONAL", etFechaHora.getText().toString(),
                        spinnerPrioridad.getSelectedItem().toString(), spinnerTipo.getSelectedItem().toString(),
                        tiempoHoras, actividadAEditar.getProgreso(), (int) actividadId,
                        actividadAEditar.getEstado(), etDescripcion.getText().toString(), etLugar.getText().toString()
                );
            }

            if (ActividadesDatos.getInstancia().actualizarActividad(actualizada)) {
                Toast.makeText(this, "Actividad actualizada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato de tiempo inválido", Toast.LENGTH_SHORT).show();
        }
    }

    // --- MÉTODOS DE APOYO (REUTILIZADOS) ---

    private void seleccionarEnSpinner(Spinner spinner, String valor) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (adapter != null) {
            int pos = adapter.getPosition(valor);
            spinner.setSelection(pos);
        }
    }

    private void actualizarInterfazPorCategoria(String categoria) {
        String[] tipos;
        if ("Académica".equals(categoria)) {
            tvAsignatura.setVisibility(View.VISIBLE);
            tilAsignatura.setVisibility(View.VISIBLE);
            tvLugar.setVisibility(View.GONE);
            tilLugar.setVisibility(View.GONE);
            tipos = new String[]{"Tareas", "Examen", "Proyecto"};
        } else {
            tvAsignatura.setVisibility(View.GONE);
            tilAsignatura.setVisibility(View.GONE);
            tvLugar.setVisibility(View.VISIBLE);
            tilLugar.setVisibility(View.VISIBLE);
            tipos = new String[]{"Citas", "Ejercicio", "Hobbies"};
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        spinnerTipo.setAdapter(adapter);
        tvTipo.setVisibility(View.VISIBLE);
        spinnerTipo.setVisibility(View.VISIBLE);
    }

    private void selectorFecha() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Seleccionar Fecha").build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDateTimeCalendar.setTimeInMillis(selection);
            selectorHora();
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void selectorHora() {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12).setMinute(0).setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTitleText("Seleccionar Hora").build();

        timePicker.addOnPositiveButtonClickListener(v -> {
            selectedDateTimeCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            selectedDateTimeCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            etFechaHora.setText(sdf.format(selectedDateTimeCalendar.getTime()));
        });
        timePicker.show(getSupportFragmentManager(), "TIME_PICKER");
    }

}
