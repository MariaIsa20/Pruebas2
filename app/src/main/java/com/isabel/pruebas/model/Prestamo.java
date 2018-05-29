package com.isabel.pruebas.model;

import java.io.Serializable;

/**
 * Created by Isabel on 24/04/2018.
 */

public class Prestamo implements Serializable {
    String id;
    String nombre;
    //String telefono;
    int telefono;
    String fecha;
    String objeto;

    boolean isSelected;
    int cantidad;

    public Prestamo() {
    }

    public Prestamo(String id, String nombre, int telefono, String fecha, String objeto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
        this.objeto = objeto;

    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



}

