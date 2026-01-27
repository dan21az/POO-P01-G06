package com.espol.application.vista.sostenibilidad;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.application.R;
import com.espol.application.controlador.SostenibilidadControladora;
import com.espol.application.datos.SostenibilidadDatos;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
//Pantalla para registrar las acciones sostenibles del día actual
public class RegistroSostenibilidadActivity extends AppCompatActivity {

    private AccionesAdapter adapter; // muestra las acciones disponibles con CheckBox

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sostenibilidad_registro);
        EdgeToEdge.enable(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvRegistroSostenibilidad), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });

        TextView tvFecha = findViewById(R.id.tvFechaRegistro);
        RecyclerView rvAcciones = findViewById(R.id.rvAcciones);
        MaterialButton btnGuardar = findViewById(R.id.btnGuardar);
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);

        tvFecha.setText("(" + hoyBonito() + ")");
// Configura la lista de acciones para mostrarlas en vertical
        rvAcciones.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccionesAdapter(SostenibilidadControladora.getInstance().getAccionesDisponibles());
        rvAcciones.setAdapter(adapter);

        // Cargar acciones ya guardadas hoy desde .ser
        SostenibilidadDatos datos = SostenibilidadDatos.getInstance(this);
        ArrayList<String> ya = datos.cargarAcciones(FechaUtils.hoyIso());
        if (!ya.isEmpty()) adapter.setSeleccionadas(ya);

        btnGuardar.setOnClickListener(v -> {
            ArrayList<String> seleccionadas = adapter.getAccionesSeleccionadas();

            if (seleccionadas.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos una acción", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar en .ser
            datos.guardarAcciones(FechaUtils.hoyIso(), seleccionadas);

            Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private String hoyBonito() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }
}