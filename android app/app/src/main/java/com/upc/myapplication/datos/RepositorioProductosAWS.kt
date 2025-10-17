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
            android.util.Log.d("RepositorioAWS", "Buscando productos con consulta: '$consulta'")
            val response = apiService.buscarProductos(consulta)
            android.util.Log.d("RepositorioAWS", "Respuesta de API: ${response.code()}")
            if (response.isSuccessful) {
                val productosAWS = response.body() ?: emptyList()
                android.util.Log.d("RepositorioAWS", "Productos AWS encontrados: ${productosAWS.size}")
                
                // Para cada producto de búsqueda, obtener su imagen principal
                val productosConImagenes = productosAWS.map { productoAWS ->
                    try {
                        // Obtener la imagen principal del producto
                        val imagenResponse = apiService.obtenerImagenesProducto(productoAWS.idProducto.toString())
                        if (imagenResponse.isSuccessful) {
                            val imagenes = imagenResponse.body() ?: emptyList()
                            val imagenPrincipal = if (imagenes.isNotEmpty()) imagenes[0].urlImagen else null
                            
                            // Crear un ProductoAWS con la imagen principal
                            val productoConImagen = productoAWS.copy(
                                urlImagen = imagenPrincipal,
                                stock = 10 // Valor por defecto para búsqueda
                            )
                            productoConImagen.toProducto()
                        } else {
                            // Si no se puede obtener la imagen, usar placeholder
                            val productoConImagen = productoAWS.copy(
                                urlImagen = "https://via.placeholder.com/300x300?text=Sin+Imagen",
                                stock = 10
                            )
                            productoConImagen.toProducto()
                        }
                    } catch (e: Exception) {
                        android.util.Log.e("RepositorioAWS", "Error al obtener imagen para producto ${productoAWS.idProducto}", e)
                        // En caso de error, usar placeholder
                        val productoConImagen = productoAWS.copy(
                            urlImagen = "https://via.placeholder.com/300x300?text=Sin+Imagen",
                            stock = 10
                        )
                        productoConImagen.toProducto()
                    }
                }
                
                android.util.Log.d("RepositorioAWS", "Productos convertidos: ${productosConImagenes.size}")
                productosConImagenes
            } else {
                android.util.Log.e("RepositorioAWS", "Error en respuesta: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            android.util.Log.e("RepositorioAWS", "Error al buscar productos", e)
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
