package com.upc.myapplication.datos

import com.upc.myapplication.api.ApiClient
import com.upc.myapplication.modelo.CategoriaProducto
import com.upc.myapplication.modelo.Producto
import com.upc.myapplication.modelo.ProductoImagen
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
            val response = apiService.buscarProductos(consulta)
            if (response.isSuccessful) {
                val productosAWS = response.body() ?: emptyList()
                
                val productosConImagenes = productosAWS.map { productoAWS ->
                    try {
                        val imagenResponse = apiService.obtenerImagenesProducto(productoAWS.idProducto.toString())
                        if (imagenResponse.isSuccessful) {
                            val imagenes = imagenResponse.body() ?: emptyList()
                            val imagenPrincipal = if (imagenes.isNotEmpty()) imagenes[0].urlImagen else null
                            
                            val productoConImagen = productoAWS.copy(
                                urlImagen = imagenPrincipal,
                                stock = 10
                            )
                            productoConImagen.toProducto()
                        } else {
                            val productoConImagen = productoAWS.copy(
                                urlImagen = "https://via.placeholder.com/300x300?text=Sin+Imagen",
                                stock = 10
                            )
                            productoConImagen.toProducto()
                        }
                    } catch (e: Exception) {
                        val productoConImagen = productoAWS.copy(
                            urlImagen = "https://via.placeholder.com/300x300?text=Sin+Imagen",
                            stock = 10
                        )
                        productoConImagen.toProducto()
                    }
                }
                
                productosConImagenes
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun obtenerImagenesProducto(id: String): List<ProductoImagen> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerImagenesProducto(id)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun obtenerTodasLasCategorias(): List<CategoriaProducto> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.obtenerTodasLasCategorias()
            if (response.isSuccessful) {
                val categoriasAWS = response.body() ?: emptyList()
                categoriasAWS.map { it.toCategoriaProducto() }
            } else {
                // Si falla la API, usar categorías por defecto
                CategoriaProducto.values().toList()
            }
        } catch (e: Exception) {
            // Si hay error, usar categorías por defecto
            CategoriaProducto.values().toList()
        }
    }
}
