package com.example.empleadosaplicationtressoles.Service;


import com.example.empleadosaplicationtressoles.Modelos.Daos.LineaCarritoDTO;
import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CarritoService {
    @GET("Carrito/obtenerProductosCarrito")
    Call<List<LineaCarritoDTO>> getLineaCarrito();

    @POST("Carrito/agregarAlCarrito")
    Call<MensajeDTO> addLineaAlCarrito(@Body LineaCarritoDTO l);

    @DELETE("Carrito/borrarLinea/{id}")
    Call<MensajeDTO> borrarLinea(@Path("id") long id);
}
