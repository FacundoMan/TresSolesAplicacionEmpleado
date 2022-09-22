package com.example.empleadosaplicationtressoles.Modelos.Daos;

public class ProductoLista {
    private Long id;
    private String nombre;
    private double precio;
    private String urlImagen;



    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
