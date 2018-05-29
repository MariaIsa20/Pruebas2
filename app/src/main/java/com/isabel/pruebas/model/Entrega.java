package com.isabel.pruebas.model;

/**
 * Created by Isabel on 28/05/2018.
 */

public class Entrega {
    String id_mesa;
    String nombre;
    int cantidad;

    public Entrega() {
    }

    public Entrega(String id_mesa, String nombre, int cantidad) {
        this.id_mesa = id_mesa;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(String id_mesa) {
        this.id_mesa = id_mesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
