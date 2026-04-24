package com.example.prestamolab.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prestamolab.entitys.Categoria;

import java.util.List;

@Dao
public interface DAOCategoria {

    @Insert
    void insertar(Categoria categoria);

    @Update
    void actualizar(Categoria categoria);

    @Delete
    void eliminar(Categoria categoria);

    @Query("SELECT * FROM Categoria")
    List<Categoria> obtenerTodos();

    @Query("SELECT * FROM Categoria WHERE id = :id")
    Categoria obtenerPorId(int id);
}