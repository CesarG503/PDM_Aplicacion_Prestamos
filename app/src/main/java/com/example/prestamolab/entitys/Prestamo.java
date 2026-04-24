package com.example.prestamolab.entitys;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Prestamo",
        foreignKeys = {
                @ForeignKey(entity = Articulo.class,
                        parentColumns = "id",
                        childColumns = "id_articulo",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Personas.class,
                        parentColumns = "id",
                        childColumns = "id_persona",
                        onDelete = ForeignKey.CASCADE)
        })
public class Prestamo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int id_articulo;
    public int id_persona;
    public String fecha_prestamo;
    public String fecha_devolucion_estimada;
    public boolean devuelto;

    public Prestamo() {}

    public Prestamo(int id_articulo, int id_persona, String fecha_prestamo, String fecha_devolucion_estimada) {
        this.id_articulo = id_articulo;
        this.id_persona = id_persona;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion_estimada = fecha_devolucion_estimada;
        this.devuelto = false;
    }
}