package com.example.prestamolab.entitys;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Articulo",
        foreignKeys = @ForeignKey(entity = Categoria.class,
                parentColumns = "id",
                childColumns = "id_categoria",
                onDelete = ForeignKey.RESTRICT))
public class Articulo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String descripcion;
    public int id_categoria;
    public boolean prestado;

    public Articulo() {}

    public Articulo(String nombre, String descripcion, int id_categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
        this.prestado = false;
    }
}