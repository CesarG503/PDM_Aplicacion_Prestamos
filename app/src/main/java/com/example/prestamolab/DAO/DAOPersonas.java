package com.example.prestamolab.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prestamolab.entitys.Personas;

import java.util.List;

@Dao
public interface DAOPersonas {

    @Insert
    void insertar(Personas personas);

    @Update
    void actualizar(Personas personas);

    @Delete
    void eliminar(Personas personas);

    @Query("SELECT * FROM Personas")
    List<Personas> obtenerTodos();

    @Query("SELECT * FROM Personas WHERE id = :id")
    Personas obtenerPorId(int id);
}