package com.upc.myapplication.modelo

import java.io.Serializable

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imageUrl: String,
    val categoria: CategoriaProducto,
    val marca: String,
    val stock: Int,
    val calificacion: Double,
    val caracteristicas: List<String>
) : Serializable

enum class CategoriaProducto(val nombre: String) : Serializable {
    CELULARES("Celulares"),
    CAMARAS("Cámaras"),
    PROYECTORES("Proyectores"),
    LAPTOPS("Laptops"),
    AUDIFONOS("Audífonos"),
    TABLETS("Tablets"),
    TODOS("Todos")
}
