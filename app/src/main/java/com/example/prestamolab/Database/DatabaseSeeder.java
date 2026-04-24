package com.example.prestamolab.Database;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.prestamolab.entitys.Articulo;
import com.example.prestamolab.entitys.Categoria;
import com.example.prestamolab.entitys.Personas;
import com.example.prestamolab.entitys.Prestamo;

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

            db.categoriaDao().insertar(new Categoria("Herramientas"));
            db.categoriaDao().insertar(new Categoria("Libros"));
            db.categoriaDao().insertar(new Categoria("Electrónicos"));
            db.categoriaDao().insertar(new Categoria("Deportes"));


            db.personasDao().insertar(new Personas("Juan Pérez", "7777-1111"));
            db.personasDao().insertar(new Personas("María López", "7777-2222"));
            db.personasDao().insertar(new Personas("Carlos Gómez", "7777-3333"));


            db.articuloDao().insertar(new Articulo("Martillo", "Martillo de acero", 1));
            db.articuloDao().insertar(new Articulo("Calculadora", "Calculadora científica", 3));
            db.articuloDao().insertar(new Articulo("Libro de Java", "Guía completa de Java", 2));
            db.articuloDao().insertar(new Articulo("Pelota de Fútbol", "Marca Wilson", 4));

            Prestamo activo = new Prestamo(1, 1, "2023-10-01", "2023-10-15");
            activo.devuelto = false;
            db.prestamoDao().insertar(activo);

            Articulo a1 = db.articuloDao().obtenerPorId(1);
            if (a1 != null) {
                a1.prestado = true;
                db.articuloDao().actualizar(a1);
            }

            Prestamo historial = new Prestamo(2, 2, "2023-09-01", "2023-09-10");
            historial.devuelto = true;
            db.prestamoDao().insertar(historial);

        });
    }
}
