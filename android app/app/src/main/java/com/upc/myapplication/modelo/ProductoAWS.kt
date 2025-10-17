package com.upc.myapplication.modelo

import com.google.gson.annotations.SerializedName

data class ProductoAWS(
    @SerializedName("idproducto")
    val idProducto: Int,
    
    @SerializedName("nombre_producto")
    val nombreProducto: String,
    
    @SerializedName("descripcion")
    val descripcion: String,
    
    @SerializedName("precio")
    val precio: Double,
    
    @SerializedName("stock")
    val stock: Int,
    
    @SerializedName("categoria")
    val categoria: String,
    
    @SerializedName("marca")
    val marca: String,
    
    @SerializedName("UrlImagen")
    val urlImagen: String
) {
    fun toProducto(): Producto {
        return Producto(
            id = idProducto.toString(),
            nombre = nombreProducto,
            precio = precio,
            categoria = mapCategoria(categoria),
            marca = marca,
            descripcion = descripcion,
            imageUrl = urlImagen,
            stock = stock,
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
