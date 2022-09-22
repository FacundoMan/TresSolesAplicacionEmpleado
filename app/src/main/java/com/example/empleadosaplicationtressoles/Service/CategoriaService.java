package com.example.empleadosaplicationtressoles.Service;


import com.example.empleadosaplicationtressoles.Modelos.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaService {
    @GET("Categoria/getCategorias")
    Call<List<Categoria>> getCategorias();
}
