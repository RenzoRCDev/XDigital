package com.upc.myapplication.modelo

data class CarritoItem(
    val producto: Producto,
    val cantidad: Int
) {
    fun obtenerPrecioTotal(): Double = producto.precio * cantidad
}
