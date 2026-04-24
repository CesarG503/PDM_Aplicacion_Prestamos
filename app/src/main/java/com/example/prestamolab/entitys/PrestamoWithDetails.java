package com.example.prestamolab.entitys;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PrestamoWithDetails {
    @Embedded
    public Prestamo prestamo;

    @Relation(
            parentColumn = "id_articulo",
            entityColumn = "id"
    )
    public Articulo articulo;

    @Relation(
            parentColumn = "id_persona",
            entityColumn = "id"
    )
    public Personas persona;
}