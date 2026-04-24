package com.example.prestamolab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.entitys.PrestamoWithDetails;
import java.util.List;

public class PrestamoAdapter extends RecyclerView.Adapter<PrestamoAdapter.PrestamoViewHolder> {

    private List<PrestamoWithDetails> prestamos;
    private OnLoanClickListener listener;

    public interface OnLoanClickListener {
        void onLoanClick(PrestamoWithDetails loan);
    }

    public PrestamoAdapter(List<PrestamoWithDetails> prestamos, OnLoanClickListener listener) {
        this.prestamos = prestamos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PrestamoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new PrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrestamoViewHolder holder, int position) {
        PrestamoWithDetails loan = prestamos.get(position);
        String articuloNombre = loan.articulo != null ? loan.articulo.nombre : "Desconocido";
        String personaNombre = loan.persona != null ? loan.persona.nombre : "Desconocido";
        
        holder.text1.setText(articuloNombre + " -> " + personaNombre);
        holder.text2.setText("Desde: " + loan.prestamo.fecha_prestamo + " | Dev. Est: " + loan.prestamo.fecha_devolucion_estimada);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onLoanClick(loan);
        });
    }

    @Override
    public int getItemCount() {
        return prestamos.size();
    }

    static class PrestamoViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        PrestamoViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}