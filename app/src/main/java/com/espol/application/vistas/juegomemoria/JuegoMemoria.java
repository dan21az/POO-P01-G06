package com.espol.application.vistas.juegomemoria;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.espol.application.R;
import com.espol.application.modelos.juegomemoria.Carta;
import com.espol.application.modelos.juegomemoria.Juego;


public class JuegoMemoria extends AppCompatActivity {

    private Juego juego;
    private GridLayout grid;
    private MaterialButton[] botones;
    private int primeraSeleccion = -1;
    private TextView txtIntentos;
    private LinearLayout pantallaInicio;
    private Button btnIniciar;
    private Button btnSalir;
    private boolean bloqueado = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_memoria);

        pantallaInicio = findViewById(R.id.pantallaInicio);
        btnIniciar = findViewById(R.id.btnIniciar);
        grid = findViewById(R.id.grid);
        txtIntentos = findViewById(R.id.txtIntentos);

        btnIniciar.setOnClickListener(v -> {
            // Ocultar pantalla inicial
            pantallaInicio.setVisibility(View.GONE);

            // Mostrar el juego
            grid.setVisibility(View.VISIBLE);
            txtIntentos.setVisibility(View.VISIBLE);

            iniciarJuego();
        });
        btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(v -> finish());

    }

    private void iniciarJuego() {
        juego = new Juego();

        botones = new MaterialButton[16];
        grid.removeAllViews();
        bloqueado = true;

        for (int i = 0; i < 16; i++) {
            //Boton que será la carta
            MaterialButton btn = new MaterialButton(this);
            int index = i;

            // Configurar la grid (tablero)
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; //Ancho de la carta, 0 para que se reparta por el grid
            params.height = 230; //Altura de la carta
            params.setMargins(12, 12, 12, 12); //Separacion entre cartas

            //Las cartas (rejillas de la grid-tablero) ocupen lo mismo
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            btn.setLayoutParams(params);


            //Tamaños del boton
            btn.setMinWidth(0);
            btn.setMinHeight(0);
            btn.setPadding(0, 0, 0, 0); //Quita el espacio interno del boton
            //Elimina los espacio de la carta
            btn.setInsetTop(0);
            btn.setInsetBottom(0);
            // Redondeado, borde y color la carta
            btn.setCornerRadius(20);
            btn.setStrokeWidth(0);
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.verdeOnSurface));
            btn.setTextColor(ContextCompat.getColor(this, R.color.verdeSurface));
            // Estado Oculta ("?") centrado y grande
            btn.setTextSize(32);
            btn.setGravity(Gravity.CENTER);

            juego.getTablero().getCarta(i).descubrir();

            //Al seleccionar una carta
            btn.setOnClickListener(v -> {

                if (bloqueado) return;
                if (index == primeraSeleccion) return;

                Carta carta = juego.getTablero().getCarta(index);
                if (carta.estaEmparejada() || carta.estaDescubierta()) return;

                carta.descubrir();
                actualizarUI();

                if (primeraSeleccion == -1) {
                    // Primer click
                    primeraSeleccion = index;
                } else {
                    // Segundo click
                    int segundaSeleccion = index;
                    bloqueado = true;

                    Carta primeraCarta = juego.getTablero().getCarta(primeraSeleccion);
                    Carta segundaCarta = carta;

                    juego.seleccionarCartas(primeraSeleccion, segundaSeleccion);

                    if (primeraCarta.getImagenId() != segundaCarta.getImagenId()) {
                        // NO coinciden → ocultar después de 1 segundo
                        new android.os.Handler().postDelayed(() -> {
                            primeraCarta.ocultar();
                            segundaCarta.ocultar();
                            actualizarUI();
                            bloqueado = false;
                        }, 1000);
                    } else {
                        // Coinciden → quedan visibles
                        bloqueado = false;
                        actualizarUI();
                    }

                    primeraSeleccion = -1;
                }
            });

            btnSalir.setVisibility(View.VISIBLE);

            botones[i] = btn;
            grid.addView(btn);
        }

        actualizarUI();

        new android.os.Handler().postDelayed(() -> {
            for (int i = 0; i < 16; i++) {
                juego.getTablero().getCarta(i).ocultar();
            }
            actualizarUI(); // Vuelve a poner los "?"
            bloqueado = false; // Desbloquea el juego para el usuario
        }, 1500); // 1500 milisegundos (puedes ajustarlo)
    }
    private int contarParesEncontrados() {
        int cartasEmparejadas = 0;

        for (int i = 0; i < 16; i++) {
            if (juego.getTablero().getCarta(i).estaEmparejada()) {
                cartasEmparejadas++;
            }
        }

        return cartasEmparejadas / 2;
    }

    private void actualizarUI() {
        int paresEncontrados = contarParesEncontrados();
        txtIntentos.setText("Intentos: " + juego.getIntentos() + " | Pares: " + paresEncontrados + "/8");


        for (int i = 0; i < 16; i++) {
            Carta c = juego.getTablero().getCarta(i);

            if (c.estaDescubierta() || c.estaEmparejada()) {
                botones[i].setText("");
                botones[i].setIconResource(c.getImagenId());

                //Tamaño y posición del icono en la carta revelada
                botones[i].setIconSize(120);
                botones[i].setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                botones[i].setIconPadding(0);

                //Color del icono y carta revelada
                botones[i].setBackgroundColor(ContextCompat.getColor(this, R.color.verdePrimaryContainer));
                botones[i].setIconTint(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.verdeOnSurface)));

            } else {

                botones[i].setText("?"); // Mostrar ?
                botones[i].setIcon(null); //Ocultar icono
                botones[i].setBackgroundColor(ContextCompat.getColor(this, R.color.verdeOnSurface));
                botones[i].setTextColor(ContextCompat.getColor(this, R.color.verdeSurface));
            }

        }
        if (juego.juegoTerminado()) {
            btnSalir.setVisibility(View.VISIBLE);
        }
        if (contarParesEncontrados() == 8) {
            mostrarDialogoVictoria();
        }

    }
    private void mostrarDialogoVictoria() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("🎉 Felicidades")
                .setMessage("Has terminado el juego")
                .setCancelable(false)
                .setPositiveButton("Aceptar y salir", (dialog, which) -> {
                    finish();
                })
                .show();
    }

    private void volverAlInicio() {
        // Resetear estado
        primeraSeleccion = -1;
        bloqueado = false;

        // Ocultar juego
        grid.setVisibility(View.GONE);
        txtIntentos.setVisibility(View.GONE);
        btnSalir.setVisibility(View.GONE);

        // Mostrar pantalla inicial
        pantallaInicio.setVisibility(View.VISIBLE);
    }

}