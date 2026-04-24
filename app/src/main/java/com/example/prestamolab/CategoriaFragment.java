package com.example.prestamolab;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        adapter = new CategoriaAdapter(categoriaList);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showAddCategoryDialog());

        return view;
    }

    private void showAddCategoryDialog() {
        EditText input = new EditText(getContext());
        input.setHint("Nombre de la categoría");
        
        new AlertDialog.Builder(getContext())
                .setTitle("Nueva Categoría")
                .setView(input)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        saveCategoria(name);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void saveCategoria(String nombre) {
        appDataBase.databaseWriteExecutor.execute(() -> {
            db.categoriaDao().insertar(new Categoria(nombre));
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Categoría guardada", Toast.LENGTH_SHORT).show();
                loadCategorias();
            });
        });
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