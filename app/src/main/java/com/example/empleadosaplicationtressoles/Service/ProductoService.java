package com.example.empleadosaplicationtressoles.Service;


import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductoService {
    @GET("Producto/getProductos")
    Call<List<Producto>> getProductos();

    @POST("Gerente/agregarProducto")
    Call<MensajeDTO> addProducto(@Body Producto p);

   @PUT("Gerente/modificarProducto/{id}")
   Call<MensajeDTO> modificarProducto(@Body Producto p,@Path("id") long id);

    @GET("Producto/getProductos/{idCategoria}")
    Call<List<Producto>> getProductoCategoria(@Path("idCategoria") long idCategoria);

    @GET("Producto/getProductosOfertas")
    Call<List<Producto>> getProductosOfertas();

}
