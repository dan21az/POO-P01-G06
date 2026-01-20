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

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import com.espol.application.R;
import com.espol.application.datos.ActividadesDatos;
import com.espol.application.modelo.actividad.Academica;
import com.espol.application.modelo.actividad.Actividad;
import com.espol.application.modelo.actividad.Personal;

import java.util.Calendar;

public class IngresarActividad extends AppCompatActivity {
    private Spinner spinnerCategoria, spinnerPrioridad, spinnerTipo;
    private TextInputLayout tilNombre, tilDescripcion, tilTiempoEstimado, tilFechaHora, tilAsignatura, tilLugar;
    private TextInputEditText etNombre, etDescripcion, etTiempoEstimado, etAsignatura, etLugar;
    private AutoCompleteTextView etFechaHora;
    private Button btnGuardar, btnCancelar;
    private TextView tvTipo, tvAsignatura, tvLugar;
    private Calendar selectedDateTimeCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_ingresar_actividad);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tv_titulo), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });
        EdgeToEdge.enable(this);

        //Obtener la lista para añadir una actividad
        ActividadesDatos.getInstancia().inicializar(this);

        // Inicializar Botones y textos
        btnGuardar = findViewById(R.id.btn_guardar);
        btnCancelar = findViewById(R.id.btn_cancelar);
        tilNombre = findViewById(R.id.til_nombre);
        tilDescripcion = findViewById(R.id.til_descripcion);
        tilFechaHora = findViewById(R.id.til_fecha_hora);
        tilTiempoEstimado = findViewById(R.id.til_tiempo_estimado);
        tilAsignatura = findViewById(R.id.til_asignatura);
        tilLugar = findViewById(R.id.til_lugar);

        // Inicialización de los Spinners
        spinnerCategoria = findViewById(R.id.spinner_categoria);
        spinnerPrioridad = findViewById(R.id.spinner_prioridad);
        spinnerTipo = findViewById(R.id.spinner_tipo);

        // Inicializar los campos de texto
        etNombre = (TextInputEditText) tilNombre.getEditText();
        etDescripcion = (TextInputEditText) tilDescripcion.getEditText();
        etTiempoEstimado = (TextInputEditText) tilTiempoEstimado.getEditText();
        etFechaHora = (AutoCompleteTextView) tilFechaHora.getEditText();
        etAsignatura = (TextInputEditText) tilAsignatura.getEditText();
        etLugar = (TextInputEditText) tilLugar.getEditText();

        tvTipo = findViewById(R.id.tv_tipo);
        tvAsignatura = findViewById(R.id.tv_asignatura);
        tvLugar = findViewById(R.id.tv_lugar);

        // Configurar Spinners
        configurarSpinners();

        // Configurar Selector de Fecha/Hora
        etFechaHora.setOnClickListener(v -> selectorFecha());

        // Configurar Botones
        btnGuardar.setOnClickListener(v -> guardarActividad());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void configurarSpinners() {
        // Categorias
        String[] categoriasPrincipales = {"Personal", "Académica"};

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoriasPrincipales);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(catAdapter);

        // Al cambiar de Categoria, actualziar la interfaz
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = (String) parent.getItemAtPosition(position);
                actualizarInterfazPorCategoria(categoriaSeleccionada);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Prioridad
        String[] prioridades = {"Alta", "Media", "Baja"};
        ArrayAdapter<String> prioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, prioridades);
        prioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridad.setAdapter(prioAdapter);

    }

    // Gestionar las opciones según la categoria
    private void actualizarInterfazPorCategoria(String categoria) {
        String[] tiposDetallados;

        if ("Académica".equals(categoria)) {
            // Mostrar campos de academmica
            tvTipo.setVisibility(View.VISIBLE);
            spinnerTipo.setVisibility(View.VISIBLE);
            tvAsignatura.setVisibility(View.VISIBLE);
            tilAsignatura.setVisibility(View.VISIBLE);
            tiposDetallados = new String[] {"Tareas", "Examen", "Proyecto"};
            // Ocultar campos de personal
            tvLugar.setVisibility(View.GONE);
            tilLugar.setVisibility(View.GONE);

        } else { // Personal
            // Ocultar campos de academica
            tvAsignatura.setVisibility(View.GONE);
            tilAsignatura.setVisibility(View.GONE);
            // Mostrar campos de personal
            tvLugar.setVisibility(View.VISIBLE);
            tilLugar.setVisibility(View.VISIBLE);
            tvTipo.setVisibility(View.VISIBLE);
            spinnerTipo.setVisibility(View.VISIBLE);
            tiposDetallados = new String[] {"Citas", "Ejercicio", "Hobbies"};

        }

        // Configurar el spinner de tipo
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposDetallados);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(tipoAdapter);

    }

    private void selectorFecha() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar Fecha")
                .build();

        // Manejar la selección de la fecha
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Guardar la fecha seleccionada
            selectedDateTimeCalendar.setTimeInMillis(selection);
            selectedDateTimeCalendar.set(Calendar.HOUR_OF_DAY, 0);
            selectedDateTimeCalendar.set(Calendar.MINUTE, 0);

            selectorHora(); // Mostrar el selector de hora
        });

        // 3. Mostrar el selector
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }

    private void selectorHora() {
        // Obtener la hora previamente seleccionada
        int initialHour = selectedDateTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int initialMinute = selectedDateTimeCalendar.get(Calendar.MINUTE);

        // Crear el selector de Hora
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(initialHour)
                .setMinute(initialMinute)
                .setTitleText("Seleccionar Hora")
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();

        // Manejar la selección de la hora
        timePicker.addOnPositiveButtonClickListener(v -> {
            // Actualizar el Calendar con la hora seleccionada
            selectedDateTimeCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            selectedDateTimeCalendar.set(Calendar.MINUTE, timePicker.getMinute());

            // Actualizar el campo de texto con la fecha y hora final
            actualizarFechaHora(selectedDateTimeCalendar);
        });

        timePicker.show(getSupportFragmentManager(), "TIME_PICKER_TAG");
    }

    private void actualizarFechaHora(Calendar c) {
        String formato = "dd/MM/yyyy HH:mm";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(formato);
        etFechaHora.setText(sdf.format(c.getTime()));
    }


    private void guardarActividad() {

        // 1. Limpiar errores previos
        tilNombre.setError(null);
        tilDescripcion.setError(null);
        tilFechaHora.setError(null);
        tilTiempoEstimado.setError(null);
        tilAsignatura.setError(null);
        tilLugar.setError(null);

        // Recoger datos comunes
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String prioridad = spinnerPrioridad.getSelectedItem().toString();
        String categoriaPrincipal = spinnerCategoria.getSelectedItem().toString();
        String tipoDetallado = spinnerTipo.getSelectedItem().toString();
        String fechaHoraVencimiento = etFechaHora.getText().toString().trim();
        String tiempoEstimadoStr = etTiempoEstimado.getText().toString().trim();
        String lugar = "";
        String asignatura = "";

        // Validacion (en caso de error mostrar mensajes)
        boolean hayError = false;

        // 3. Validaciones rigurosas
        if (nombre.isEmpty()) {
            tilNombre.setError("El nombre es obligatorio.");
            hayError = true;
        }
        if (descripcion.isEmpty()) {
            tilDescripcion.setError("La descripción es obligatoria");
            hayError = true;
        }
        if (fechaHoraVencimiento.isEmpty()) {
            tilFechaHora.setError("La fecha es obligatoria");
            hayError = true;
        }
        if (tiempoEstimadoStr.isEmpty()) {
            tilTiempoEstimado.setError("El tiempo estimado es obligatorio.");
            hayError = true;
        }
        // Obtener datos especificos
        if ("Académica".equals(categoriaPrincipal)) {
            asignatura = etAsignatura.getText().toString().trim();
            if (asignatura.isEmpty()) {
                tilAsignatura.setError("La Asignatura es obligatoria.");
                hayError = true;
            }
        } else { // Personal
            lugar = etLugar.getText().toString().trim();
            if(lugar.isEmpty()){
                tilLugar.setError("El lugar es obligatorio");
                hayError = true;
            }
        }

        if (hayError){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float tiempoEstimado = Float.valueOf(tiempoEstimadoStr)/60;
            // Crear la nueva actividad
            Actividad nuevaActividad;
            Actividad.aumentarId();
            int nuevoId = Actividad.getContadorId();

            if ("Académica".equals(categoriaPrincipal)) { // Crear Academica
                nuevaActividad = new Academica(
                        nombre, "ACADEMICA", fechaHoraVencimiento, prioridad, tipoDetallado, tiempoEstimado, 0, nuevoId, "No iniciado", descripcion, asignatura
                );
            } else { // Crear Personal
                nuevaActividad = new Personal(nombre, "PERSONAL", fechaHoraVencimiento, prioridad, tipoDetallado, tiempoEstimado, 0, nuevoId, "No iniciado", descripcion, lugar);
            }

            // Guardar en el repositorio
            ActividadesDatos.getInstancia().agregarActividad(nuevaActividad);
            Toast.makeText(this, "Actividad guardada con éxito (ID: " + nuevoId + ").", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "El tiempo estimado debe ser un número ", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar la actividad ", Toast.LENGTH_LONG).show();
        }
    }
}