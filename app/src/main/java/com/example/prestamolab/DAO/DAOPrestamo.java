package com.example.prestamolab.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.prestamolab.entitys.Prestamo;
import com.example.prestamolab.entitys.PrestamoWithDetails;

import java.util.List;

@Dao
public interface DAOPrestamo {

    @Insert
    void insertar(Prestamo prestamo);

    @Update
    void actualizar(Prestamo prestamo);

    @Transaction
    @Query("SELECT * FROM Prestamo")
    List<PrestamoWithDetails> obtenerTodos();

    @Transaction
    @Query("SELECT * FROM Prestamo WHERE devuelto = 0")
    List<PrestamoWithDetails> obtenerPrestamosActivos();

    @Transaction
    @Query("SELECT * FROM Prestamo WHERE devuelto = 1")
    List<PrestamoWithDetails> obtenerHistorial();
}