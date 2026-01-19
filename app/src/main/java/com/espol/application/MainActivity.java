package com.espol.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.espol.application.vistas.hidratacion.HidratacionMenu;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;

import com.espol.application.vistas.juegomemoria.JuegoMemoria;
import com.espol.application.vistas.actividad.ListaActividades;
import com.espol.application.vistas.sostenibilidad.MainSostenibilidadActivity;


public class MainActivity extends AppCompatActivity {

    private MaterialCardView cardActividades, cardHidratacion, cardSostenibilidad, cardMemoria;
    private MaterialButton btnAjustes, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_main);

        // Inicializar tarjetas
        cardActividades = findViewById(R.id.card_actividades);
        cardHidratacion = findViewById(R.id.card_hidratacion);
        cardSostenibilidad = findViewById(R.id.card_sostenibilidad);
        cardMemoria = findViewById(R.id.card_memoria);

        // Inicializar botones
        btnAjustes = findViewById(R.id.btn_ajustes);
        btnSalir = findViewById(R.id.btn_salir);

        // Acciones de las tarjetas
        cardActividades.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaActividades.class);
            startActivity(intent);
        });

        cardHidratacion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HidratacionMenu.class);
            startActivity(intent);
        });

        cardSostenibilidad.setOnClickListener(v -> {Intent intent = new Intent(MainActivity.this, MainSostenibilidadActivity.class);
            startActivity(intent);
        });


        cardMemoria.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JuegoMemoria.class);
            startActivity(intent);
        });

        // Acciones de los Botones
        btnAjustes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AjustesActivity.class);
            startActivity(intent);
        });

        btnSalir.setOnClickListener(v -> {
            finish(); // Cierra la app
        });
    }

    // Mensaje para funciones en desarrollo
    private void mostrarFuncionEnDesarrollo() {
        Toast.makeText(this, "Función en desarrollo...", Toast.LENGTH_SHORT).show();
    }
}
