package com.espol.application.vistas.actividad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.espol.application.R;
import com.espol.application.datos.ActividadesDatos;
import com.espol.application.modelos.actividad.Academica;
import com.espol.application.modelos.actividad.Actividad;
import com.espol.application.modelos.actividad.Personal;
import com.espol.application.vistas.sesionEnfoque.SesionEnfoqueActivity;

import java.util.ArrayList;
import java.util.List;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private List<Actividad> listaActividades;

    public ActividadAdapter(ArrayList<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public void setLista(List<Actividad> nuevaLista) {
        this.listaActividades = nuevaLista;
        notifyDataSetChanged(); // Notifica que la lista a cambiado
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {
        Actividad actividad = listaActividades.get(position);

        // Mostrar datos comunes
        String idNombreHtml = "<b>ID:</b> " + actividad.getId() + " | <b>Nombre:</b> " + actividad.getNombre();
        holder.tvIdNombre.setText(android.text.Html.fromHtml(idNombreHtml,0));
        String fechaHtml = "<b>Fecha de vencimiento:</b> " + actividad.getFechaVencimiento();
        holder.tvFechaVencimiento.setText(android.text.Html.fromHtml(fechaHtml,0));
        String prioridadHtml = "<b>Prioridad:</b> " + actividad.getPrioridad();
        holder.tvPrioridad.setText(android.text.Html.fromHtml(prioridadHtml,0));
        String avanceHtml = "<b>Avance:</b> " + actividad.getProgreso() + "%";
        holder.tvAvance.setText(android.text.Html.fromHtml(avanceHtml,0));
        String tipoHtml = "<b>Tipo:</b> " + actividad.getTipo();
        holder.tvTipo.setText(android.text.Html.fromHtml(tipoHtml,0));

        // Mostrar datos especificos de las clases
        if (actividad instanceof Personal) {
            Personal personal = (Personal) actividad;
            String lugar = personal.getLugar();
            String lugarHtml = "<b>Lugar:</b> " + lugar;
            holder.tvLugar.setText(android.text.Html.fromHtml(lugarHtml,0));
            holder.tvLugar.setVisibility(View.VISIBLE);
            // Ocultar botones de enfoque para actividades personales
            holder.btnPomodoro.setVisibility(View.GONE);
            holder.btnDeepWork.setVisibility(View.GONE);

        } else if (actividad instanceof Academica) { // Para actividad academicas
            holder.tvLugar.setVisibility(View.GONE); // Ocultar lugar
            // Mostrar botones de enfoque
            holder.btnPomodoro.setVisibility(View.VISIBLE);
            holder.btnDeepWork.setVisibility(View.VISIBLE);
            holder.btnPomodoro.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, SesionEnfoqueActivity.class); // <-- Clase unificada
                intent.putExtra(SesionEnfoqueActivity.EXTRA_ACTIVIDAD, actividad);
                intent.putExtra(SesionEnfoqueActivity.EXTRA_MODO, "POMODORO"); // <-- Modo Pomodoro
                context.startActivity(intent);
            });
            holder.btnDeepWork.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, SesionEnfoqueActivity.class); // <-- Misma clase unificada
                intent.putExtra(SesionEnfoqueActivity.EXTRA_ACTIVIDAD, actividad);
                intent.putExtra(SesionEnfoqueActivity.EXTRA_MODO, "DEEPWORK"); // <-- Modo Deep Work
                context.startActivity(intent);
            });
        }

        holder.btnVerDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetalleActividad.class);
            // Pasar el ID a Detalle Actividad e iniciar
            intent.putExtra(DetalleActividad.EXTRA_ACTIVIDAD_ID, (long) actividad.getId());
            v.getContext().startActivity(intent);
        });

        if (actividad.getProgreso() < 100) {
            // La actividad NO está completada. Mostrar el botón.
            holder.btnRegistrarAvance.setVisibility(View.VISIBLE);

            // Configurar el Listener SÓLO si el botón es visible
            holder.btnRegistrarAvance.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), RegistrarAvance.class);
                //Pasar el ID de la actividad usando la constante definida en RegistrarAvanceActivity
                intent.putExtra(RegistrarAvance.EXTRA_ID_ACTIVIDAD, (long) actividad.getId());
                // Iniciar la Activity
                v.getContext().startActivity(intent);
            });

        } else { // Ocultar el boton de avance para actividades completadas
            holder.btnRegistrarAvance.setVisibility(View.GONE);
            holder.btnRegistrarAvance.setOnClickListener(null);
        }

        holder.btnEliminar.setOnClickListener(v -> {
            // Obtener el ID del elemento específico de esta posición
            long idActividadAEliminar = listaActividades.get(holder.getBindingAdapterPosition()).getId();
            // Construir y mostrar el AlertDialog
            mostrarDialogoConfirmacion(v.getContext(), idActividadAEliminar, holder.getBindingAdapterPosition());
        });

    }

    private void mostrarDialogoConfirmacion(Context context, long idActividad, int position) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Confirmar Eliminación") // Título del diálogo
                .setMessage("¿Estás seguro de que quieres eliminar esta actividad? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Eliminar la actividad
                    if (ActividadesDatos.getInstancia().eliminarActividad(idActividad)) {
                        listaActividades.remove(position);
                        notifyItemRemoved(position); //Notificar que la lista ha cambiado
                        Toast.makeText(context, "Actividad eliminada.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error: No se encontró la actividad para eliminar.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss(); // Cerrar dialogo
                })
                .show(); // Mostrar dialogo
    }



    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
        // Recicla las vistas para mostrar los items
    public static class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdNombre, tvFechaVencimiento, tvPrioridad, tvAvance, tvTipo, tvLugar;
        Button btnVerDetalles, btnRegistrarAvance, btnEliminar, btnPomodoro, btnDeepWork;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdNombre = itemView.findViewById(R.id.tv_id_nombre);
            tvFechaVencimiento = itemView.findViewById(R.id.tv_fecha_vencimiento);
            tvPrioridad = itemView.findViewById(R.id.tv_prioridad);
            tvAvance = itemView.findViewById(R.id.tv_avance);
            tvTipo = itemView.findViewById(R.id.tv_tipo);
            tvLugar = itemView.findViewById(R.id.tv_lugar);
            btnVerDetalles = itemView.findViewById(R.id.btn_ver_detalles);
            btnRegistrarAvance = itemView.findViewById(R.id.btn_registrar_avance);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
            btnPomodoro = itemView.findViewById(R.id.btn_iniciar_pomodoro);
            btnDeepWork = itemView.findViewById(R.id.btn_iniciar_deep_work);
        }
    }
}
