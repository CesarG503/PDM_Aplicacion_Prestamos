package com.example.prestamolab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Personas;
import com.google.android.material.textfield.TextInputEditText;

public class InsertPersonaActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etContacto;
    private Button btnGuardar, btnEliminar, btnCancelar;
    private appDataBase db;
    private int personaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_persona);

        Toolbar toolbar = findViewById(R.id.toolbarPersona);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = appDataBase.getINSTANCE(this);

        etNombre = findViewById(R.id.etNombre);
        etContacto = findViewById(R.id.etContacto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnCancelar = findViewById(R.id.btnCancelar);

        if (getIntent().hasExtra("id")) {
            personaId = getIntent().getIntExtra("id", -1);
            loadPersonaData();
            btnEliminar.setVisibility(View.VISIBLE);
        }

        btnGuardar.setOnClickListener(v -> savePersona());
        btnCancelar.setOnClickListener(v -> finish());
        btnEliminar.setOnClickListener(v -> deletePersona());
    }

    private void loadPersonaData() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            Personas persona = db.personasDao().obtenerPorId(personaId);
            if (persona != null) {
                runOnUiThread(() -> {
                    etNombre.setText(persona.nombre);
                    etContacto.setText(persona.contacto);
                });
            }
        });
    }

    private void savePersona() {
        String nombre = etNombre.getText().toString().trim();
        String contacto = etContacto.getText().toString().trim();

        if (nombre.isEmpty() || contacto.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        appDataBase.databaseWriteExecutor.execute(() -> {
            if (personaId == -1) {
                db.personasDao().insertar(new Personas(nombre, contacto));
            } else {
                Personas persona = new Personas(nombre, contacto);
                persona.id = personaId;
                db.personasDao().actualizar(persona);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Persona guardada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void deletePersona() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            Personas persona = new Personas();
            persona.id = personaId;
            db.personasDao().eliminar(persona);
            runOnUiThread(() -> {
                Toast.makeText(this, "Persona eliminada", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}