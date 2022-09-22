package com.example.empleadosaplicationtressoles.Modelos.Daos;

import java.util.Date;

public class PedidoDTO{
    private String nombre;
    private String apellido;
    private Date fecha;
    private String contacto;
    private String direccion;
    private String descripcionCasa;
    private long cambio;
    private String envioORetiro;
    private String formaDePago;






    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcionCasa() {
        return descripcionCasa;
    }

    public void setDescripcionCasa(String descripcionCasa) {
        this.descripcionCasa = descripcionCasa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getCambio() {
        return cambio;
    }

    public void setCambio(long cambio) {
        this.cambio = cambio;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }


    public String getEnvioORetiro() {
        return envioORetiro;
    }

    public void setEnvioORetiro(String envioORetiro) {
        this.envioORetiro = envioORetiro;
    }
}
