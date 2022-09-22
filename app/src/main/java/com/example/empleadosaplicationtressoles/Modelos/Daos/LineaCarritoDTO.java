package com.example.empleadosaplicationtressoles.Modelos.Daos;


import com.example.empleadosaplicationtressoles.Modelos.Producto;

public class LineaCarritoDTO {
    private Long id;
    private int cantidad;
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
