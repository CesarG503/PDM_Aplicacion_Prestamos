package com.example.prestamolab.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.prestamolab.DAO.DAOArticulo;
import com.example.prestamolab.DAO.DAOCategoria;
import com.example.prestamolab.entitys.Articulo;
import com.example.prestamolab.entitys.Categoria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Articulo.class,Categoria.class}, version = 1, exportSchema = false)
public abstract class appDataBase extends RoomDatabase {

    public abstract DAOCategoria categoriaDao();
    public abstract DAOArticulo articuloDao();

    private static volatile appDataBase INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static appDataBase getINSTANCE(Context context){
        if(INSTANCE == null){
            synchronized (appDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            appDataBase.class,
                            "db_prestamos"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }

}