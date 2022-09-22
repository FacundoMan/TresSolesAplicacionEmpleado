package com.example.empleadosaplicationtressoles.Service;


import com.example.empleadosaplicationtressoles.Modelos.Daos.LoginDTO;
import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Pedido;
import com.example.empleadosaplicationtressoles.Modelos.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {

    @POST("aut/registrarUsuario")
    Call<UsuarioDTO> addUsuario(@Body UsuarioDTO u);
    @POST("aut/iniciarSesion")
    Call<LoginDTO> login(@Body LoginDTO l);

    @GET("Gerente/obtenerEmpleados")
    Call<List<UsuarioDTO>> getEmpleados();

    @POST("Gerente/registrarPicker")
    Call<MensajeDTO> registrarPicker(@Body UsuarioDTO u);
    @POST("Gerente/registrarRepartidor")
    Call<MensajeDTO> registrarRepartidor(@Body UsuarioDTO u);

    @PUT("Usuario/modoficarCelular/{celular}")
    Call<MensajeDTO> modificarCelular( @Path("celular") String celular);

    @PUT("Usuario/modoficarPassword")
    Call<MensajeDTO> modificarPassword( @Body UsuarioDTO u);

}
