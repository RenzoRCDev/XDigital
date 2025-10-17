package com.upc.myapplication.datos

import com.upc.myapplication.api.ApiClient
import com.upc.myapplication.modelo.CategoriaProducto
import com.upc.myapplication.modelo.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositorioProductosAWS {
    
    private val apiService = ApiClient.productoApiService
    
    suspend fun obtenerTodosLosProductos(): List<Producto> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerTodosLosProductos()
            if (response.isSuccessful) {
                val productosAWS = response.body() ?: emptyList()
                productosAWS.map { it.toProducto() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun obtenerProductosPorCategoria(categoria: CategoriaProducto): List<Producto> = withContext(Dispatchers.IO) {
        try {
            // Como la API no tiene filtro por categoría, obtenemos todos y filtramos localmente
            val todosLosProductos = obtenerTodosLosProductos()
            todosLosProductos.filter { it.categoria == categoria }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun buscarProductos(consulta: String): List<Producto> = withContext(Dispatchers.IO) {
        try {
            // Como la API no tiene búsqueda, obtenemos todos y filtramos localmente
            val todosLosProductos = obtenerTodosLosProductos()
            todosLosProductos.filter { 
                it.nombre.contains(consulta, ignoreCase = true) ||
                it.marca.contains(consulta, ignoreCase = true) ||
                it.descripcion.contains(consulta, ignoreCase = true)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
