package com.example.prestamolab;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.entitys.Categoria;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> categorias;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onEditClick(Categoria categoria);
        void onDeleteClick(Categoria categoria);
    }

    public CategoriaAdapter(List<Categoria> categorias, OnCategoriaClickListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_with_edit, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.text1.setText(categoria.nombre_categoria);
        holder.text2.setVisibility(View.GONE);

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(categoria);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Eliminar Categoría")
                    .setMessage("¿Estás seguro de eliminar esta categoría?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        if (listener != null) listener.onDeleteClick(categoria);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ImageButton btnEdit, btnDelete;
        CategoriaViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text_main);
            text2 = itemView.findViewById(R.id.text_secondary);
            btnEdit = itemView.findViewById(R.id.btnEditItem);
            btnDelete = itemView.findViewById(R.id.btnDeleteItem);
        }
    }
}