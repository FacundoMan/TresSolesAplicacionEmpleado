package com.example.empleadosaplicationtressoles.Service;



import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoService {
    @GET("PedidoUsuario/obtenerPedidos")
    Call<List<Pedido>> getPedidos();

    @PUT("PedidoUsuario/cancelarPedido/{id}")
    Call<MensajeDTO> cancelarPedido(@Path("id") long id);

    @GET("Pedido/getPedidosByEstado/{idEstado}")
    Call<List<Pedido>> getPedidoPorEstadoId(@Path("idEstado") long idEstado);

    @GET("Pedido/todosLosPedidos")
    Call<List<Pedido>> getTodosLosPedidos();

    @GET("Picker/pedidosPicker")
    Call<List<Pedido>> getPedidoPicker();

    @GET("Repartidor/pedidosRepartidor")
    Call<List<Pedido>> getPedidosRepartidor();

    @PUT("Pedido/cambiarEstado/{idPedido}/{idEstado}")
    Call<MensajeDTO> cambiarEstado(@Path("idPedido") long idPedido,@Path("idEstado") long idEstado);
}
