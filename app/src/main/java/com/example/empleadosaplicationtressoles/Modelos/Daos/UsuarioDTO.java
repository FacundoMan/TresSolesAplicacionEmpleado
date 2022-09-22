package com.example.empleadosaplicationtressoles.Modelos.Daos;

import com.example.empleadosaplicationtressoles.Modelos.Rol;

import java.io.Serializable;
import java.util.List;

public class UsuarioDTO implements Serializable {
    private String nombreUsuario;
    private String password;
    private String numeroContacto;
    private String nombre;
    private String apellido;
    private String Mensaje;
    private List<Rol> roles;


    public UsuarioDTO() {
    }


    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
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

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }


}
