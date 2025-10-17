package com.upc.myapplication.api

import com.upc.myapplication.modelo.ProductoAWS
import retrofit2.Response
import retrofit2.http.GET

interface ProductoApiService {
    
    @GET("listarproductos")
    suspend fun obtenerTodosLosProductos(): Response<List<ProductoAWS>>
}
