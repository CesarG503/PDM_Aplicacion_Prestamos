package com.example.prestamolab.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prestamolab.entitys.Articulo;

import java.util.List;

@Dao
public interface DAOArticulo {

    @Insert
    void insertar(Articulo articulo);

    @Update
    void actualizar(Articulo articulo);

    @Delete
    void eliminar(Articulo articulo);

    @Query("SELECT * FROM Articulo")
    List<Articulo> obtenerTodos();

    @Query("SELECT * FROM Articulo WHERE id = :id")
    Articulo obtenerPorId(int id);
}