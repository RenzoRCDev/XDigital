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
    LAPTOPS("Laptops"),
    CELULARES("Smartphones"),
    TABLETS("Tablets"),
    MONITORES("Monitores"),
    AUDIFONOS("Auriculares"),
    TECLADOS("Teclados"),
    MOUSES("Mouses"),
    SMARTWATCHES("Smartwatches"),
    ALMACENAMIENTO("Almacenamiento"),
    ACCESORIOS("Accesorios"),
    CAMARAS("Cámaras"),
    PROYECTORES("Proyectores"),
    TODOS("Todos")
}
