package com.example.prestamolab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.entitys.Articulo;
import java.util.List;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder> {

    private List<Articulo> articulos;

    public ArticuloAdapter(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_with_edit, parent, false);
        return new ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo articulo = articulos.get(position);
        holder.text1.setText(articulo.nombre);
        holder.text2.setText(articulo.descripcion + (articulo.prestado ? " (Prestado)" : " (Disponible)"));

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InsertArticuloActivity.class);
            intent.putExtra("id", articulo.id);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    static class ArticuloViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ImageButton btnEdit;
        ArticuloViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text_main);
            text2 = itemView.findViewById(R.id.text_secondary);
            btnEdit = itemView.findViewById(R.id.btnEditItem);
        }
    }
}