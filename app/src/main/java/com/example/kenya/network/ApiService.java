package com.example.kenya.network;

import com.example.kenya.model.ApiResponseList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // Para obtener un solo producto (si aún lo necesitas)
    // @GET("productos/{id}")
    // Call<ApiResponse> getProducto(@Path("id") int id);

    // Para obtener todos los productos
    @GET("productos")
    Call<ApiResponseList> getProductos();
}
