package com.isabel.pruebas.model;

/**
 * Created by Isabel on 23/05/2018.
 */

public class Pedido {

    String id_pedido;
    String id_usuario;
    int valor;
    int cantidad;
    String id_mesa;
    String nombre;


    public Pedido( String id_pedido,  String id_usuario, int valor, int cantidad, String id_mesa, String nombre) {

        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.valor = valor;
        this.cantidad = cantidad;
        this.id_mesa = id_mesa;
        this.nombre = nombre;


    }

    public Pedido() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(String id_mesa) {
        this.id_mesa = id_mesa;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
