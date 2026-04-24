package com.example.prestamolab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.entitys.PrestamoWithDetails;
import java.util.List;

public class PrestamoAdapter extends RecyclerView.Adapter<PrestamoAdapter.PrestamoViewHolder> {

    private List<PrestamoWithDetails> prestamos;
    private OnLoanClickListener listener;

    private static final int TYPE_ACTIVE = 0;
    private static final int TYPE_HISTORY = 1;

    public interface OnLoanClickListener {
        void onLoanClick(PrestamoWithDetails loan);
    }

    public PrestamoAdapter(List<PrestamoWithDetails> prestamos, OnLoanClickListener listener) {
        this.prestamos = prestamos;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return prestamos.get(position).prestamo.devuelto ? TYPE_HISTORY : TYPE_ACTIVE;
    }

    @NonNull
    @Override
    public PrestamoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = (viewType == TYPE_HISTORY) ? R.layout.item_prestamo_history : R.layout.item_prestamo;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new PrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrestamoViewHolder holder, int position) {
        PrestamoWithDetails loan = prestamos.get(position);
        String articuloNombre = loan.articulo != null ? loan.articulo.nombre : "Desconocido";
        String personaNombre = loan.persona != null ? loan.persona.nombre : "Desconocido";
        
        holder.tvArticulo.setText(articuloNombre);
        
        if (loan.prestamo.devuelto) {
            holder.tvPersona.setText("Fue prestado a: " + personaNombre);
            holder.tvFechas.setText("Devuelto el: " + loan.prestamo.fecha_devolucion_estimada);
            // El icono ya está definido en el XML de historia, pero podemos reforzarlo
            if (holder.ivStatusIcon != null) {
                holder.ivStatusIcon.setImageResource(R.drawable.ic_return_check);
                holder.ivStatusIcon.setColorFilter(0xFF6C757D);
            }
        } else {
            holder.tvPersona.setText("Prestado a: " + personaNombre);
            holder.tvFechas.setText(loan.prestamo.fecha_prestamo + " - " + loan.prestamo.fecha_devolucion_estimada);
            
            if (holder.ivStatusIcon != null) {
                holder.ivStatusIcon.setImageResource(android.R.drawable.ic_dialog_info);
                holder.ivStatusIcon.setColorFilter(0xFF006878);
            }

            if (holder.btnReturn != null) {
                holder.btnReturn.setOnClickListener(v -> {
                    if (listener != null) listener.onLoanClick(loan);
                });
            }

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onLoanClick(loan);
            });
        }
    }

    @Override
    public int getItemCount() {
        return prestamos.size();
    }

    static class PrestamoViewHolder extends RecyclerView.ViewHolder {
        TextView tvArticulo, tvPersona, tvFechas;
        ImageView ivStatusIcon;
        ImageButton btnReturn;

        PrestamoViewHolder(View itemView) {
            super(itemView);
            tvArticulo = itemView.findViewById(R.id.tvArticulo);
            tvPersona = itemView.findViewById(R.id.tvPersona);
            tvFechas = itemView.findViewById(R.id.tvFechas);
            ivStatusIcon = itemView.findViewById(R.id.ivStatusIcon);
            btnReturn = itemView.findViewById(R.id.btnReturn);
        }
    }
}