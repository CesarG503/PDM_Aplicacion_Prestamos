package com.example.prestamolab.Database;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.prestamolab.entitys.Articulo;
import com.example.prestamolab.entitys.Categoria;
import com.example.prestamolab.entitys.Personas;

public class DatabaseSeeder {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_FIRST_RUN = "isFirstRun";

    public static void checkAndSeed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.getBoolean(KEY_FIRST_RUN, true)) {
            seedData(context);
            prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        }
    }

    private static void seedData(Context context) {
        appDataBase db = appDataBase.getINSTANCE(context);
        appDataBase.databaseWriteExecutor.execute(() -> {
            // Insertar Categorías de prueba
            db.categoriaDao().insertar(new Categoria("Herramientas"));
            db.categoriaDao().insertar(new Categoria("Libros"));
            db.categoriaDao().insertar(new Categoria("Electrónicos"));
            db.categoriaDao().insertar(new Categoria("Deportes"));

            // Insertar Personas de prueba
            db.personasDao().insertar(new Personas("Juan Pérez", "7777-1111"));
            db.personasDao().insertar(new Personas("María López", "7777-2222"));
            db.personasDao().insertar(new Personas("Carlos Gómez", "7777-3333"));

            // Insertar Artículos de prueba (Asumiendo que los IDs de categoría inician en 1)
            db.articuloDao().insertar(new Articulo("Martillo", "Martillo de acero", 1));
            db.articuloDao().insertar(new Articulo("Calculadora", "Calculadora científica", 3));
            db.articuloDao().insertar(new Articulo("Libro de Java", "Guía completa de Java", 2));
            db.articuloDao().insertar(new Articulo("Pelota de Fútbol", "Marca Wilson", 4));
        });
    }
}
