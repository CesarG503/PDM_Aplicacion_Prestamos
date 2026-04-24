package com.example.prestamolab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Categoria;
import com.google.android.material.textfield.TextInputEditText;

public class InsertCategoriaActivity extends AppCompatActivity {

    private TextInputEditText etNombre;
    private Button btnGuardar, btnCancelar, btnEliminar;
    private appDataBase db;
    private int categoriaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_categoria);

        Toolbar toolbar = findViewById(R.id.toolbarCategoria);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = appDataBase.getINSTANCE(this);

        etNombre = findViewById(R.id.etNombreCategoria);
        btnGuardar = findViewById(R.id.btnGuardarCategoria);
        btnCancelar = findViewById(R.id.btnCancelarCategoria);
        btnEliminar = findViewById(R.id.btnEliminarCategoria);

        if (getIntent().hasExtra("id")) {
            categoriaId = getIntent().getIntExtra("id", -1);
            loadCategoriaData();
            btnEliminar.setVisibility(View.VISIBLE);
        }

        btnGuardar.setOnClickListener(v -> saveCategoria());
        btnCancelar.setOnClickListener(v -> finish());
        btnEliminar.setOnClickListener(v -> deleteCategoria());
    }

    private void loadCategoriaData() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            Categoria categoria = db.categoriaDao().obtenerPorId(categoriaId);
            if (categoria != null) {
                runOnUiThread(() -> {
                    etNombre.setText(categoria.nombre_categoria);
                });
            }
        });
    }

    private void saveCategoria() {
        String nombre = etNombre.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre es requerido", Toast.LENGTH_SHORT).show();
            return;
        }

        appDataBase.databaseWriteExecutor.execute(() -> {
            if (categoriaId == -1) {
                db.categoriaDao().insertar(new Categoria(nombre));
            } else {
                Categoria categoria = new Categoria(nombre);
                categoria.id = categoriaId;
                db.categoriaDao().actualizar(categoria);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Categoría guardada", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void deleteCategoria() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            try {
                Categoria categoria = new Categoria();
                categoria.id = categoriaId;
                db.categoriaDao().eliminar(categoria);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Categoría eliminada", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> 
                    Toast.makeText(this, "Error: Puede que existan artículos usando esta categoría", Toast.LENGTH_LONG).show()
                );
            }
        });
    }
}