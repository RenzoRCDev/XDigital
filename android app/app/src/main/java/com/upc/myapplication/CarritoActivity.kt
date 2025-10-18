package com.upc.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.upc.myapplication.adaptadores.AdaptadorCarrito
import com.upc.myapplication.datos.CarritoManager
import com.upc.myapplication.modelo.CarritoItem

class CarritoActivity : AppCompatActivity() {
    
    private lateinit var adaptadorCarrito: AdaptadorCarrito
    // El carrito ahora se maneja con CarritoManager
    
    private lateinit var recyclerCarrito: RecyclerView
    private lateinit var botonProcederPago: MaterialButton
    private lateinit var botonContinuarComprando: MaterialButton
    private lateinit var textoTotalItems: android.widget.TextView
    private lateinit var textoTotalPrecio: android.widget.TextView
    private lateinit var layoutCarritoVacio: android.widget.LinearLayout
    private lateinit var layoutCarritoLleno: android.widget.LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)
        
        CarritoManager.init(this)
        
        inicializarVistas()
        configurarToolbar()
        configurarRecyclerView()
        cargarCarrito()
        configurarBotones()
    }
    
    private fun inicializarVistas() {
        recyclerCarrito = findViewById(R.id.recycler_carrito)
        botonProcederPago = findViewById(R.id.boton_proceder_pago)
        botonContinuarComprando = findViewById(R.id.boton_continuar_comprando)
        textoTotalItems = findViewById(R.id.texto_total_items)
        textoTotalPrecio = findViewById(R.id.texto_total_precio)
        layoutCarritoVacio = findViewById(R.id.layout_carrito_vacio)
        layoutCarritoLleno = findViewById(R.id.layout_carrito_lleno)
    }
    
    private fun configurarToolbar() {
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.mi_carrito)
    }
    
    private fun configurarRecyclerView() {
        adaptadorCarrito = AdaptadorCarrito(
            CarritoManager.obtenerItems(),
            onCantidadCambiada = { item, nuevaCantidad ->
                actualizarCantidad(item, nuevaCantidad)
            },
            onEliminarItem = { item ->
                eliminarItem(item)
            }
        )
        
        recyclerCarrito.layoutManager = LinearLayoutManager(this)
        recyclerCarrito.adapter = adaptadorCarrito
    }
    
    private fun cargarCarrito() {
        actualizarUI()
    }
    
    private fun configurarBotones() {
        botonProcederPago.setOnClickListener {
            if (CarritoManager.obtenerItems().isNotEmpty()) {
                procederAlPago()
            } else {
                Toast.makeText(this, getString(R.string.carrito_vacio), Toast.LENGTH_SHORT).show()
            }
        }
        
        botonContinuarComprando.setOnClickListener {
            finish()
        }
    }
    
    private fun actualizarCantidad(item: CarritoItem, nuevaCantidad: Int) {
        CarritoManager.actualizarCantidad(item.producto.id, nuevaCantidad)
        actualizarUI()
    }
    
    private fun eliminarItem(item: CarritoItem) {
        CarritoManager.eliminarItem(item.producto.id)
        actualizarUI()
    }
    
    private fun actualizarTotal() {
        val totalItems = CarritoManager.obtenerCantidadTotal()
        val totalPrecio = CarritoManager.obtenerTotal()
        
        textoTotalItems.text = "$totalItems items"
        textoTotalPrecio.text = "${getString(R.string.moneda)} ${String.format("%.2f", totalPrecio)}"
        
        botonProcederPago.isEnabled = CarritoManager.obtenerItems().isNotEmpty()
        
        if (CarritoManager.obtenerItems().isNotEmpty()) {
            mostrarCarritoLleno()
        }
    }
    
    private fun mostrarCarritoVacio() {
        layoutCarritoVacio.visibility = android.view.View.VISIBLE
        layoutCarritoLleno.visibility = android.view.View.GONE
    }
    
    private fun mostrarCarritoLleno() {
        layoutCarritoVacio.visibility = android.view.View.GONE
        layoutCarritoLleno.visibility = android.view.View.VISIBLE
    }
    
    private fun actualizarUI() {
        val items = CarritoManager.obtenerItems()
        adaptadorCarrito = AdaptadorCarrito(
            items,
            onCantidadCambiada = { item, nuevaCantidad ->
                actualizarCantidad(item, nuevaCantidad)
            },
            onEliminarItem = { item ->
                eliminarItem(item)
            }
        )
        recyclerCarrito.adapter = adaptadorCarrito
        actualizarTotal()
        
        if (items.isEmpty()) {
            mostrarCarritoVacio()
        } else {
            mostrarCarritoLleno()
        }
    }
    
    private fun procederAlPago() {
        Toast.makeText(this, "Procediendo al pago...", Toast.LENGTH_SHORT).show()
        // Aquí se implementaría la lógica de pago
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
