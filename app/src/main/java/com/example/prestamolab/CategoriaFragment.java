package com.example.prestamolab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Categoria;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class CategoriaFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoriaAdapter adapter;
    private List<Categoria> categoriaList = new ArrayList<>();
    private appDataBase db;

    public CategoriaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);

        recyclerView = view.findViewById(R.id.rvCategorias);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddCategoria);

        db = appDataBase.getINSTANCE(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoriaAdapter(categoriaList, new CategoriaAdapter.OnCategoriaClickListener() {
            @Override
            public void onEditClick(Categoria categoria) {
                openEditCategoryActivity(categoria);
            }

            @Override
            public void onDeleteClick(Categoria categoria) {
                eliminarCategoria(categoria);
            }
        });
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InsertCategoriaActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void eliminarCategoria(Categoria categoria) {
        appDataBase.databaseWriteExecutor.execute(() -> {
            try {
                db.categoriaDao().eliminar(categoria);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Categoría eliminada", Toast.LENGTH_SHORT).show();
                        loadCategorias();
                    });
                }
            } catch (Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error: La categoría podría estar en uso", Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void openEditCategoryActivity(Categoria categoria) {
        Intent intent = new Intent(getActivity(), InsertCategoriaActivity.class);
        intent.putExtra("id", categoria.id);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategorias();
    }

    private void loadCategorias() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            List<Categoria> list = db.categoriaDao().obtenerTodos();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    categoriaList.clear();
                    categoriaList.addAll(list);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }
}