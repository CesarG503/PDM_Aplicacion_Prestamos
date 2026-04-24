package com.example.prestamolab;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Articulo;
import com.example.prestamolab.entitys.Personas;
import com.example.prestamolab.entitys.Prestamo;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class InsertPrestamoActivity extends AppCompatActivity {

    private Spinner spArticulo, spPersona;
    private TextInputEditText etFechaPrestamo, etFechaDevolucion;
    private Button btnGuardar;
    private appDataBase db;
    private List<Articulo> articulosDisponibles = new ArrayList<>();
    private List<Personas> personasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_prestamo);

        db = appDataBase.getINSTANCE(this);

        spArticulo = findViewById(R.id.spArticulo);
        spPersona = findViewById(R.id.spPersona);
        etFechaPrestamo = findViewById(R.id.etFechaPrestamo);
        etFechaDevolucion = findViewById(R.id.etFechaDevolucion);
        btnGuardar = findViewById(R.id.btnGuardarPrestamo);

        loadSpinners();

        btnGuardar.setOnClickListener(v -> savePrestamo());
    }

    private void loadSpinners() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            articulosDisponibles = db.articuloDao().obtenerDisponibles();
            personasList = db.personasDao().obtenerTodos();

            List<String> nombresArticulos = new ArrayList<>();
            for (Articulo a : articulosDisponibles) nombresArticulos.add(a.nombre);

            List<String> nombresPersonas = new ArrayList<>();
            for (Personas p : personasList) nombresPersonas.add(p.nombre);

            runOnUiThread(() -> {
                ArrayAdapter<String> adapterArt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresArticulos);
                adapterArt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spArticulo.setAdapter(adapterArt);

                ArrayAdapter<String> adapterPer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresPersonas);
                adapterPer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPersona.setAdapter(adapterPer);
            });
        });
    }

    private void savePrestamo() {
        int artPos = spArticulo.getSelectedItemPosition();
        int perPos = spPersona.getSelectedItemPosition();
        String fechaP = etFechaPrestamo.getText().toString().trim();
        String fechaD = etFechaDevolucion.getText().toString().trim();

        if (artPos == -1 || perPos == -1 || fechaP.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Articulo articulo = articulosDisponibles.get(artPos);
        Personas persona = personasList.get(perPos);

        appDataBase.databaseWriteExecutor.execute(() -> {
            Prestamo prestamo = new Prestamo(articulo.id, persona.id, fechaP, fechaD);
            db.prestamoDao().insertar(prestamo);
            
            articulo.prestado = true;
            db.articuloDao().actualizar(articulo);

            runOnUiThread(() -> {
                Toast.makeText(this, "Préstamo registrado", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}