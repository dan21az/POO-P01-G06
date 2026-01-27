package com.espol.application.vista.hidratacion;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.application.R;
import com.espol.application.datos.HidratacionDatos;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Locale;


public class RegistroTomaActivity extends AppCompatActivity {
    private Calendar horaSeleccionada;
    private HidratacionDatos controladora;
    private EditText etCantidad, etHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidratacion_registro_toma);
        EdgeToEdge.enable(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvRegisterTitle), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });

        controladora = HidratacionDatos.getInstance(this);

        etCantidad = findViewById(R.id.etCantidad);
        etHora = findViewById(R.id.etHora);
        MaterialButton btnGuardar = findViewById(R.id.btnGuardarToma);
        MaterialButton btnCancelar = findViewById(R.id.btnCancelarToma);

        etHora.setOnClickListener(v -> mostrarTimePicker());

        btnGuardar.setOnClickListener(v -> {
            String cantStr = etCantidad.getText().toString().trim();

            if (cantStr.isEmpty()) {
                Toast.makeText(this, "Ingrese la cantidad", Toast.LENGTH_SHORT).show();
                return;
            }

            if (horaSeleccionada == null) {
                Toast.makeText(this, "Seleccione la hora", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int cantidad = Integer.parseInt(cantStr);

                controladora.registrarToma(cantidad, "alguna_id", horaSeleccionada);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String horaLegible = sdf.format(horaSeleccionada.getTime());

                Toast.makeText(this, "¡Toma registrada a las " + horaLegible + "!", Toast.LENGTH_SHORT).show();
                finish();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Número no válido", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void mostrarTimePicker() {
        Calendar c = Calendar.getInstance();

        // Crear la instancia del MaterialTimePicker
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H) // O CLOCK_12H
                .setHour(c.get(Calendar.HOUR_OF_DAY))
                .setMinute(c.get(Calendar.MINUTE))
                .setTitleText("Selecciona la hora")
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialTimePicker)
                .build();

        // Cuando el usuario presiona "OK"
        timePicker.addOnPositiveButtonClickListener(v -> {
            int hourOfDay = timePicker.getHour();
            int minute = timePicker.getMinute();

            horaSeleccionada = Calendar.getInstance();
            horaSeleccionada.set(Calendar.HOUR_OF_DAY, hourOfDay);
            horaSeleccionada.set(Calendar.MINUTE, minute);

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());

            etHora.setText(sdf.format(horaSeleccionada.getTime()));
        });

        // Mostrar el picker
        timePicker.show(getSupportFragmentManager(), "MATERIAL_TIME_PICKER");
    }
}
