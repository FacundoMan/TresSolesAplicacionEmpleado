package com.example.empleadosaplicationtressoles.Modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {
    private Long id;
    private Date fecha;
    private String descripcionCasa;
    private String direccion;
    private String contacto;
    private String nombre;
    private String apellido;
    private long cambio;
    private String formaDePago;
    private String tiempoEstimado;
    private String envioORetiro;
    private double total;
    private Estado estado;
    private List<LineaDePedido> lineasPedido=new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getEnvioORetiro() {
        return envioORetiro;
    }

    public void setEnvioORetiro(String envioORetiro) {
        this.envioORetiro = envioORetiro;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<LineaDePedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaDePedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public double calcularTotal(){
        double ret=0;
        for (LineaDePedido l:getLineasPedido()) {
            ret=ret+l.calcularTotalLinea();
        }
        return ret;
    }
}
