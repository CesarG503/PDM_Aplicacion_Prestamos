package com.example.prestamolab;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Articulo;
import com.example.prestamolab.entitys.Categoria;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class InsertArticuloActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etDescription;
    private Spinner spCategoria;
    private Button btnGuardar, btnCancelar;
    private appDataBase db;
    private List<Categoria> categoriasList = new ArrayList<>();
    private int articuloId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_articulo);

        Toolbar toolbar = findViewById(R.id.toolbarArticulo);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = appDataBase.getINSTANCE(this);

        etNombre = findViewById(R.id.etNombreArticulo);
        etDescription = findViewById(R.id.etDescripcion);
        spCategoria = findViewById(R.id.spCategoria);
        btnGuardar = findViewById(R.id.btnGuardarArticulo);
        btnCancelar = findViewById(R.id.btnCancelarArticulo);

        loadCategorias();

        if (getIntent().hasExtra("id")) {
            articuloId = getIntent().getIntExtra("id", -1);
            loadArticuloData();
        }

        btnGuardar.setOnClickListener(v -> saveArticulo());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void loadArticuloData() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            Articulo articulo = db.articuloDao().obtenerPorId(articuloId);
            if (articulo != null) {
                runOnUiThread(() -> {
                    etNombre.setText(articulo.nombre);
                    etDescription.setText(articulo.descripcion);
                    // Selection for spinner is handled after categories load usually
                });
            }
        });
    }

    private void loadCategorias() {
        appDataBase.databaseWriteExecutor.execute(() -> {
            categoriasList = db.categoriaDao().obtenerTodos();
            List<String> nombresCategorias = new ArrayList<>();
            for (Categoria c : categoriasList) {
                nombresCategorias.add(c.nombre_categoria);
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresCategorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategoria.setAdapter(adapter);
            });
        });
    }

    private void saveArticulo() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescription.getText().toString().trim();
        int selectedPos = spCategoria.getSelectedItemPosition();

        if (nombre.isEmpty() || selectedPos == -1) {
            Toast.makeText(this, "Nombre y Categoría son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        int idCategoria = categoriasList.get(selectedPos).id;

        appDataBase.databaseWriteExecutor.execute(() -> {
            if (articuloId == -1) {
                Articulo articulo = new Articulo(nombre, descripcion, idCategoria);
                db.articuloDao().insertar(articulo);
            } else {
                Articulo articulo = db.articuloDao().obtenerPorId(articuloId);
                articulo.nombre = nombre;
                articulo.descripcion = descripcion;
                articulo.id_categoria = idCategoria;
                db.articuloDao().actualizar(articulo);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Artículo guardado", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}