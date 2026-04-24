package com.example.prestamolab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo articulo = articulos.get(position);
        holder.text1.setText(articulo.nombre);
        holder.text2.setText(articulo.descripcion + (articulo.prestado ? " (Prestado)" : " (Disponible)"));
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    static class ArticuloViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ArticuloViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}