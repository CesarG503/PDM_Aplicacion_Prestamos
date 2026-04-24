package com.example.prestamolab.entitys;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categoria")
public class Categoria {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre_categoria;

    public Categoria(int id, String nombre_categoria) {
        this.id = id;
        this.nombre_categoria = nombre_categoria;
    }

    public Categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public Categoria()
    {

    }
}