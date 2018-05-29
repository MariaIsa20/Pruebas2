package com.isabel.pruebas.model;

/**
 * Created by Isabel on 28/05/2018.
 */

public class Ordenes {
    String id_orden;
    String id_mesa;
    String nombre;
    int cantidad;
    String estado;

    public Ordenes() {
    }

    public Ordenes(String id_orden, String id_mesa, String nombre, int cantidad, String estado) {
        this.id_orden = id_orden;
        this.id_mesa = id_mesa;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public String getId_orden() {
        return id_orden;
    }

    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
