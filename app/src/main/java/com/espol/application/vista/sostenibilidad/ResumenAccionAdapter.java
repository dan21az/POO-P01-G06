package com.espol.application.vista.sostenibilidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.application.modelo.sostenibilidad.FrecuenciaItem;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import com.espol.application.R;
//resumen semanal de acciones sostenibles con su frecuencia, logro y color
public class ResumenAccionAdapter extends RecyclerView.Adapter<ResumenAccionAdapter.VH> {

    private final ArrayList<FrecuenciaItem> items;  // Lista de filas del resumen


    public ResumenAccionAdapter(ArrayList<FrecuenciaItem> items) {
        this.items = (items == null) ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sosteniblidad_frecuencia_accion, parent, false);
        return new VH(v);
    }

    @Override
    //Asigna los valores de acción, frecuencia y logro a cada fila
    public void onBindViewHolder(@NonNull VH holder, int position) {
        FrecuenciaItem it = items.get(position);

        holder.tvAccion.setText(it.accion);
        holder.tvVeces.setText(it.veces + " / " + it.total);

        holder.btnLogro.setText(it.logro);
        holder.btnLogro.setBackgroundTintList(android.content.res.ColorStateList.valueOf(it.color));
        // Obtener el contexto de la vista
        Context context = holder.itemView.getContext();

        if (it.color ==  ContextCompat.getColor(context, R.color.rojoPrimary)) {
            holder.btnLogro.setTextColor(ContextCompat.getColor(context, R.color.rojoPrimaryOn));
        } else if ((it.color ==  ContextCompat.getColor(context, R.color.verdePrimary))) {
            holder.btnLogro.setTextColor(ContextCompat.getColor(context, R.color.verdePrimaryOn));
        } else{
            holder.btnLogro.setTextColor(ContextCompat.getColor(context, R.color.naranjaPrimaryOn));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }  //Cantidad de filas del resumen
//Reemplaza la lista actual por una nueva y refresca la vista.
    public void setItems(ArrayList<FrecuenciaItem> nuevos) {
        items.clear();
        if (nuevos != null) items.addAll(nuevos);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder { //ViewHolder que contiene los componentes visuales de cada fila
        TextView tvAccion, tvVeces;
        MaterialButton btnLogro;

        VH(@NonNull View itemView) {
            super(itemView);
            tvAccion = itemView.findViewById(R.id.tvAccion);
            tvVeces = itemView.findViewById(R.id.tvVeces);
            btnLogro = itemView.findViewById(R.id.btnLogro);
        }
    }
}
