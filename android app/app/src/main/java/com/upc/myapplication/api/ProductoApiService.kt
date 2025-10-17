package com.upc.myapplication.api

import com.upc.myapplication.modelo.CategoriaAWS
import com.upc.myapplication.modelo.ProductoAWS
import com.upc.myapplication.modelo.ProductoImagen
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductoApiService {
    
    @GET("listarproductos")
    suspend fun obtenerTodosLosProductos(): Response<List<ProductoAWS>>
    
    @GET("listarimagenes")
    suspend fun obtenerImagenesProducto(@Query("id") id: String): Response<List<ProductoImagen>>
    
    @GET("listarcategorias")
    suspend fun obtenerTodasLasCategorias(): Response<List<CategoriaAWS>>
    
    @GET("buscarproductos")
    suspend fun buscarProductos(@Query("texto") texto: String): Response<List<ProductoAWS>>
}
