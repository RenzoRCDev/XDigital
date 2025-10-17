package com.upc.myapplication.modelo

import com.google.gson.annotations.SerializedName

data class CategoriaAWS(
    @SerializedName("nombre")
    val nombre: String
) {
    fun toCategoriaProducto(): CategoriaProducto {
        return mapCategoria(nombre)
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
