package com.espol.application.vista.hidratacion;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.application.R;
import com.espol.application.datos.HidratacionDatos;
import com.google.android.material.button.MaterialButton;


public class MetaActivity extends AppCompatActivity {

    private HidratacionDatos controladora;
    private EditText etNewMeta;
    private TextView tvCurrentMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidratacion_meta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvUpdateTitle), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), insets.top + 20, v.getPaddingRight(), v.getPaddingBottom());
            return windowInsets;
        });

        controladora = HidratacionDatos.getInstance(this);

        etNewMeta = findViewById(R.id.etNewMeta);
        tvCurrentMeta = findViewById(R.id.tvCurrentMeta);
        MaterialButton btnSave = findViewById(R.id.btnSaveMeta);
        MaterialButton btnCancel = findViewById(R.id.btnCancelMeta);

        // Mostrar meta actual
        tvCurrentMeta.setText(controladora.getMetaDiaria() + " ml");

        // Acción Guardar
        btnSave.setOnClickListener(v -> {
            String input = etNewMeta.getText().toString();
            if (!input.isEmpty()) {
                int nuevaMeta = Integer.parseInt(input);
                controladora.setMetaDiaria(nuevaMeta);

                Toast.makeText(this, "Meta actualizada", Toast.LENGTH_SHORT).show();
                finish(); // Esto cierra esta pantalla y vuelve a la anterior
            } else {
                etNewMeta.setError("Ingrese un valor");
            }
        });

        // Acción Cancelar
        btnCancel.setOnClickListener(v -> finish());
    }
}