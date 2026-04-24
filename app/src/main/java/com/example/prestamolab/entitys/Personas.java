package com.example.prestamolab.entitys;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Personas")
public class Personas {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String contacto;

    public Personas() {}

    public Personas(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
    }
}