package com.upc.myapplication.datos

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upc.myapplication.modelo.CarritoItem
import com.upc.myapplication.modelo.Producto

object CarritoManager {
    private const val PREFS_NAME = "carrito_prefs"
    private const val KEY_CARRITO = "carrito_items"
    
    private var sharedPreferences: SharedPreferences? = null
    private var gson = Gson()
    
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    private fun getCarritoItems(): MutableList<CarritoItem> {
        val json = sharedPreferences?.getString(KEY_CARRITO, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<CarritoItem>>() {}.type
            gson.fromJson(json, type) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }
    
    private fun saveCarritoItems(items: List<CarritoItem>) {
        val json = gson.toJson(items)
        sharedPreferences?.edit()?.putString(KEY_CARRITO, json)?.apply()
    }
    
    fun agregarProducto(producto: Producto) {
        val items = getCarritoItems()
        val existingItem = items.find { it.producto.id == producto.id }
        
        if (existingItem != null) {
            val index = items.indexOf(existingItem)
            items[index] = existingItem.copy(cantidad = existingItem.cantidad + 1)
        } else {
            items.add(CarritoItem(producto, 1))
        }
        
        saveCarritoItems(items)
    }
    
    fun obtenerItems(): List<CarritoItem> {
        return getCarritoItems()
    }
    
    fun eliminarItem(productoId: String) {
        val items = getCarritoItems()
        items.removeAll { it.producto.id == productoId }
        saveCarritoItems(items)
    }
    
    fun actualizarCantidad(productoId: String, cantidad: Int) {
        val items = getCarritoItems()
        val item = items.find { it.producto.id == productoId }
        if (item != null) {
            if (cantidad <= 0) {
                items.remove(item)
            } else {
                val index = items.indexOf(item)
                items[index] = item.copy(cantidad = cantidad)
            }
            saveCarritoItems(items)
        }
    }
    
    fun limpiarCarrito() {
        saveCarritoItems(emptyList())
    }
    
    fun obtenerTotal(): Double {
        return getCarritoItems().sumOf { it.obtenerPrecioTotal() }
    }
    
    fun obtenerCantidadTotal(): Int {
        return getCarritoItems().sumOf { it.cantidad }
    }
}
