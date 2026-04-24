package com.example.prestamolab.entitys;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Categoria.class, parentColumns = "id", childColumns = "id_categoria", onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.NO_ACTION))
public class Articulo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int id_categoria;

    public String descripcion;

}