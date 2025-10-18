package com.upc.myapplication.modelo

import com.google.gson.annotations.SerializedName

data class ProductoAWS(
    @SerializedName("idproducto")
    val idProducto: Int,
    
    @SerializedName("nombre")
    val nombre: String? = null, // Campo para la API de búsqueda
    
    @SerializedName("nombre_producto")
    val nombreProducto: String? = null, // Campo para la API principal
    
    @SerializedName("descripcion")
    val descripcion: String,
    
    @SerializedName("precio")
    val precio: Double,
    
    @SerializedName("stock")
    val stock: Int? = null, // Opcional para la API de búsqueda
    
    @SerializedName("categoria")
    val categoria: String,
    
    @SerializedName("marca")
    val marca: String,
    
    @SerializedName("UrlImagen")
    val urlImagen: String? = null // Opcional para la API de búsqueda
) {
    fun toProducto(): Producto {
        return Producto(
            id = idProducto.toString(),
            nombre = nombre ?: nombreProducto ?: "Producto sin nombre", // Prioriza 'nombre', luego 'nombreProducto'
            precio = precio,
            categoria = mapCategoria(categoria),
            marca = marca,
            descripcion = descripcion,
            imageUrl = urlImagen ?: "https://via.placeholder.com/300x300?text=Sin+Imagen", // Imagen placeholder
            stock = stock ?: 10, // Valor por defecto para productos de búsqueda
            calificacion = 4.5, // Valor por defecto
            caracteristicas = emptyList() // Lista vacía por defecto
        )
    }
    
    private fun mapCategoria(categoriaAWS: String): CategoriaProducto {
        return when (categoriaAWS.lowercase()) {
            "laptops" -> CategoriaProducto.LAPTOPS
            "smartphones" -> CategoriaProducto.CELULARES
            "tablets" -> CategoriaProducto.TABLETS
            "monitores" -> CategoriaProducto.MONITORES
            "auriculares" -> CategoriaProducto.AUDIFONOS
            "teclados" -> CategoriaProducto.TECLADOS
            "mouses" -> CategoriaProducto.MOUSES
            "smartwatches" -> CategoriaProducto.SMARTWATCHES
            "almacenamiento" -> CategoriaProducto.ALMACENAMIENTO
            "accesorios" -> CategoriaProducto.ACCESORIOS
            "camaras" -> CategoriaProducto.CAMARAS
            "proyectores" -> CategoriaProducto.PROYECTORES
            else -> CategoriaProducto.TODOS
        }
    }
}
