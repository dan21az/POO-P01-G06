package com.espol.application.vistas.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import com.espol.application.R;
import com.espol.application.datos.ActividadesDatos;
import com.espol.application.modelos.actividad.Actividad;

public class RegistrarAvance extends AppCompatActivity {

    public static final String EXTRA_ID_ACTIVIDAD = "com.espol.application.ID_ACTIVIDAD";
    private EditText etIdActividad, etNombreActividad,etAvanceActual;
    private TextInputLayout tilNuevoAvance;
    private Button btnGuardar, btnCancelar;

    // Datos de la actividad
    private long idActividad;
    private Actividad actividad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_registrar_avance);
        inicializarVistas();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tv_titulo_avance), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });

        // Obtener el ID de la actividad desde el Intent
        Intent intent = getIntent();
        idActividad = intent.getLongExtra(EXTRA_ID_ACTIVIDAD, -1);
        actividad = ActividadesDatos.getInstancia().buscarActividadPorId(idActividad);
        cargarDatosActividad(idActividad);

        // Configurar botones
        btnGuardar.setOnClickListener(v -> intentarGuardarAvance());
        btnCancelar.setOnClickListener(v -> finish()); // Cierra la actividad
    }

    private void inicializarVistas() { //Iniciar botones y textos
        etIdActividad = findViewById(R.id.et_id_actividad);
        etNombreActividad = findViewById(R.id.et_nombre_actividad);
        etAvanceActual = findViewById(R.id.et_avance_actual);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnCancelar = findViewById(R.id.btn_cancelar);
        tilNuevoAvance = findViewById(R.id.et_nuevo_avance);
    }

    private void cargarDatosActividad(long id) { // Mostrar los datos de la actividad
        etIdActividad.setText(String.valueOf(actividad.getId()));
        etNombreActividad.setText(actividad.getNombre());
        etAvanceActual.setText(actividad.getProgreso() + "%");
    }


    private void intentarGuardarAvance() {
        // Obtener el texto del EditText anidado
        String nuevoAvanceStr = tilNuevoAvance.getEditText().getText().toString().trim();

        // Resetear la vista en caso de error
        tilNuevoAvance.setError(null);
        tilNuevoAvance.setErrorEnabled(false);

        if (TextUtils.isEmpty(nuevoAvanceStr)) { // Validar que el campo no esté vacío
            tilNuevoAvance.setError("Ingrese un valor para el nuevo avance.");
            tilNuevoAvance.setErrorEnabled(true);
            return;
        }

        int nuevoAvance;

        try {
            nuevoAvance = Integer.parseInt(nuevoAvanceStr);
        } catch (NumberFormatException e) { // Validar que sea un número entero
            tilNuevoAvance.setError("El valor debe ser un número entero válido.");
            tilNuevoAvance.setErrorEnabled(true);
            return;
        }

        if (nuevoAvance < 0 || nuevoAvance > 100) { // Validar rango (0-100)
            tilNuevoAvance.setError("El avance debe estar entre 0 y 100.");
            tilNuevoAvance.setErrorEnabled(true);
            return;
        }

        if (nuevoAvance <= actividad.getProgreso()) { // Validar que el nuevo avance sea mayor que el actual
            String mensajeError = "El nuevo avance (" + nuevoAvance + "%) debe ser mayor al actual (" + nuevoAvance + "%).";
            tilNuevoAvance.setError(mensajeError);
            tilNuevoAvance.setErrorEnabled(true);
            return;
        }

        confirmarYGuardarAvance(nuevoAvance);
    }

    private void confirmarYGuardarAvance(int nuevoAvance) {
        String mensaje = "¿Está seguro de registrar el avance al " + nuevoAvance + "%?";
        new MaterialAlertDialogBuilder(this).setTitle("Confirmar Avance").setMessage(mensaje) // Mostrar ventana
                .setPositiveButton("Sí", (dialog, which) -> {
                    boolean exito = ActividadesDatos.getInstancia().actualizarProgreso(idActividad, nuevoAvance);
                    if (exito) {
                        Toast.makeText(this, "Avance registrado con éxito.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error al actualizar la actividad.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
