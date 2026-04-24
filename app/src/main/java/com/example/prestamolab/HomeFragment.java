package com.example.prestamolab;

import android.app.AlertDialog;
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
import com.example.prestamolab.entitys.Articulo;
import com.example.prestamolab.entitys.PrestamoWithDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvActive, rvHistory;
    private PrestamoAdapter activeAdapter, historyAdapter;
    private List<PrestamoWithDetails> activeList = new ArrayList<>();
    private List<PrestamoWithDetails> historyList = new ArrayList<>();
    private appDataBase db;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvActive = view.findViewById(R.id.rvActiveLoans);
        rvHistory = view.findViewById(R.id.rvHistoryLoans);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddLoan);

        db = appDataBase.getINSTANCE(getContext());

        rvActive.setLayoutManager(new LinearLayoutManager(getContext()));
        activeAdapter = new PrestamoAdapter(activeList, this::onActiveLoanClicked);
        rvActive.setAdapter(activeAdapter);

        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new PrestamoAdapter(historyList, null);
        rvHistory.setAdapter(historyAdapter);

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InsertPrestamoActivity.class));
        });

        return view;
    }

    private void onActiveLoanClicked(PrestamoWithDetails loan) {
        new AlertDialog.Builder(getContext())
                .setTitle("Devolución")
                .setMessage("¿Desea marcar este artículo como devuelto?")
                .setPositiveButton("Sí", (dialog, which) -> markAsReturned(loan))
                .setNegativeButton("No", null)
                .show();
    }

    private void markAsReturned(PrestamoWithDetails loan) {
        appDataBase.databaseWriteExecutor.execute(() -> {
            loan.prestamo.devuelto = true;
            db.prestamoDao().actualizar(loan.prestamo);
            
            if (loan.articulo != null) {
                loan.articulo.prestado = false;
                db.articuloDao().actualizar(loan.articulo);
            }
            
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Artículo devuelto", Toast.LENGTH_SHORT).show();
                loadLoans();
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLoans();
    }

    private void loadLoans() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            List<PrestamoWithDetails> active = db.prestamoDao().obtenerPrestamosActivos();
            List<PrestamoWithDetails> history = db.prestamoDao().obtenerHistorial();
            
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    activeList.clear();
                    activeList.addAll(active);
                    activeAdapter.notifyDataSetChanged();

                    historyList.clear();
                    historyList.addAll(history);
                    historyAdapter.notifyDataSetChanged();
                });
            }
        });
    }
}