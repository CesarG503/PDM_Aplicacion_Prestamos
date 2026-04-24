package com.example.prestamolab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Articulo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ArticuloFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticuloAdapter adapter;
    private List<Articulo> articuloList = new ArrayList<>();
    private appDataBase db;

    public ArticuloFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articulo, container, false);
        
        recyclerView = view.findViewById(R.id.recycleArticulo);
        FloatingActionButton btnInsertar = view.findViewById(R.id.btnInsertar);

        db = appDataBase.getINSTANCE(getContext());
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new ArticuloAdapter(articuloList, articulo -> {
            eliminarArticulo(articulo);
        });
        
        recyclerView.setAdapter(adapter);

        btnInsertar.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InsertArticuloActivity.class));
        });

        return view;
    }

    private void eliminarArticulo(Articulo articulo) {
        appDataBase.databaseWriteExecutor.execute(() -> {
            try {
                db.articuloDao().eliminar(articulo);
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Artículo eliminado", Toast.LENGTH_SHORT).show();
                    loadArticulos();
                });
            } catch (Exception e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "No se puede eliminar: el artículo está en uso", Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadArticulos();
    }

    private void loadArticulos() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            List<Articulo> list = db.articuloDao().obtenerTodos();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    articuloList.clear();
                    articuloList.addAll(list);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }
}