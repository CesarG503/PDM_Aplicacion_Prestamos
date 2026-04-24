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
import com.example.prestamolab.entitys.Personas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class PersonaFragment extends Fragment {

    private RecyclerView recyclerView;
    private PersonasAdapter adapter;
    private List<Personas> personasList = new ArrayList<>();
    private appDataBase db;

    public PersonaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persona, container, false);
        
        recyclerView = view.findViewById(R.id.rvPersonas);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddPersona);

        db = appDataBase.getINSTANCE(getContext());
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PersonasAdapter(personasList, persona -> {
            eliminarPersona(persona);
        });
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InsertPersonaActivity.class));
        });

        return view;
    }

    private void eliminarPersona(Personas persona) {
        appDataBase.databaseWriteExecutor.execute(() -> {
            try {
                db.personasDao().eliminar(persona);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Persona eliminada", Toast.LENGTH_SHORT).show();
                        loadPersonas();
                    });
                }
            } catch (Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error: La persona podría tener préstamos activos", Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPersonas();
    }

    private void loadPersonas() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            List<Personas> list = db.personasDao().obtenerTodos();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    personasList.clear();
                    personasList.addAll(list);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }
}