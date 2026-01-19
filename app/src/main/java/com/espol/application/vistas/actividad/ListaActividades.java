package com.espol.application.vistas.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.application.R;
import com.espol.application.datos.ActividadesDatos;
import com.espol.application.modelos.actividad.Actividad;

import java.util.ArrayList;
import java.util.List;

public class ListaActividades extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActividadAdapter adapter;
    private List<Actividad> listaActividades;
    private Spinner spinnerFiltroTipo;
    String[] tiposFiltro = new String[] {"Todos", "Tareas", "Examen", "Proyecto", "Citas", "Ejercicio", "Hobbies"};

    private String filtroActual = "Todos"; // Filtro por defecto es todos

    private Spinner spinnerOrdenar;
    String[] criteriosOrden = {"Nombre A-Z", "Fecha (desc)", "Avance (desc)"};
    private String ordenActual = "Nombre A-Z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_lista_actividades);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recycler_view_actividades);
        spinnerFiltroTipo = findViewById(R.id.spinner_filtro_tipo);
        Button btnAgregarActividad = findViewById(R.id.btn_agregar_actividad);
        btnAgregarActividad.setOnClickListener(v -> lanzarActividadIngreso());

        // Obtener Actividades del repositorio
        ActividadesDatos.getInstancia().inicializar(this);
        listaActividades = ActividadesDatos.getInstancia().getListaActividades();

        // Configurar el RecyclerView
        adapter = new ActividadAdapter((ArrayList<Actividad>) listaActividades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Configurar el spinner de filtro
        configurarSpinnerFiltro();
        spinnerOrdenar = findViewById(R.id.spinner_ordenar);
        configurarSpinnerOrden();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tv_title), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });
        EdgeToEdge.enable(this);


    }

    // Configurar el spinner de filtro
    private void configurarSpinnerFiltro() {

        // Crear el adaptador para el Spinner
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposFiltro
        );
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltroTipo.setAdapter(adapterSpinner);

        // Configurar el Listener de selección
        spinnerFiltroTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filtroSeleccionado = tiposFiltro[position];
                filtroActual = filtroSeleccionado;

                // Aplicar el filtro a la lista
                filtroActual = filtroSeleccionado;
                aplicarFiltros();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void configurarSpinnerOrden() {
        ArrayAdapter<String> adapterOrden = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                criteriosOrden
        );
        adapterOrden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrdenar.setAdapter(adapterOrden);

        spinnerOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ordenActual = criteriosOrden[position];
                aplicarFiltros();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void aplicarFiltros() {

        // 1. Filtrar por tipo
        List<Actividad> lista = ActividadesDatos.getInstancia()
                .filtrarPorTipo(filtroActual);

        // 2. Quitar vencidas
        lista = ActividadesDatos.getInstancia()
                .filtrarNoVencidas(lista);

        // 3. Ordenar
        lista = ActividadesDatos.getInstancia()
                .ordenarLista(lista, ordenActual);

        // 4. Actualizar RecyclerView
        adapter.setLista(lista);
    }

    private void lanzarActividadIngreso() {
        Intent intent = new Intent(this, IngresarActividad.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarFiltros();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}