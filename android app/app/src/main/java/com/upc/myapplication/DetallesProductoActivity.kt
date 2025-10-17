package com.upc.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import com.upc.myapplication.databinding.ActivityDetallesProductoBinding
import com.upc.myapplication.datos.CarritoManager
import com.upc.myapplication.modelo.Producto

class DetallesProductoActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDetallesProductoBinding
    private lateinit var producto: Producto
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityDetallesProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Configurar la barra de estado
        window.statusBarColor = getColor(R.color.status_bar_color)
        
        // Inicializar CarritoManager
        CarritoManager.init(this)
        
        // Obtener el producto del intent
        producto = intent.getSerializableExtra("producto") as Producto
        
        configurarToolbar()
        mostrarDetallesProducto()
        configurarBotones()
    }
    
    private fun configurarToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false) // Quitar la flecha del toolbar
        supportActionBar?.title = "Detalles del Producto"
        
        // Configurar botón flotante de regreso
        binding.fabVolver.setOnClickListener {
            finish()
        }
    }
    
    private fun mostrarDetallesProducto() {
        // Mostrar nombre del producto en el header personalizado
        val tituloHeader = findViewById<TextView>(R.id.titulo_header)
        tituloHeader?.text = producto.nombre
        
        // Cargar imagen
        Glide.with(this)
            .load(producto.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imagenProducto)
        
        // Mostrar información del producto
        binding.nombreProducto.text = producto.nombre
        binding.precioProducto.text = "${getString(R.string.moneda)} ${String.format("%.2f", producto.precio)}"
        binding.marcaProducto.text = producto.marca
        binding.calificacionProducto.text = "★ ${producto.calificacion}"
        binding.stockProducto.text = "${getString(R.string.stock)}: ${producto.stock}"
        binding.descripcionProducto.text = producto.descripcion
        
        // Mostrar características
        val caracteristicasText = producto.caracteristicas.joinToString("\n• ", "• ")
        binding.caracteristicasProducto.text = caracteristicasText
        
        // Mostrar estado del stock
        if (producto.stock > 0) {
            binding.botonAgregarCarrito.isEnabled = true
            binding.botonComprarAhora.isEnabled = true
            binding.estadoStock.text = "Disponible"
            binding.estadoStock.setTextColor(getColor(android.R.color.holo_green_dark))
        } else {
            binding.botonAgregarCarrito.isEnabled = false
            binding.botonComprarAhora.isEnabled = false
            binding.estadoStock.text = "Agotado"
            binding.estadoStock.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }
    
    private fun configurarBotones() {
        binding.botonAgregarCarrito.setOnClickListener {
            if (producto.stock > 0) {
                CarritoManager.agregarProducto(producto)
                Toast.makeText(this, getString(R.string.producto_agregado), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Producto agotado", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.botonComprarAhora.setOnClickListener {
            if (producto.stock > 0) {
                // Agregar al carrito y ir al carrito
                CarritoManager.agregarProducto(producto)
                val intent = Intent(this, CarritoActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Producto agotado", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.botonCompartir.setOnClickListener {
            compartirProducto()
        }
    }
    
    private fun compartirProducto() {
        val textoCompartir = """
            ¡Mira este producto en X-Digital Imports!
            
            ${producto.nombre}
            ${getString(R.string.moneda)} ${String.format("%.2f", producto.precio)}
            
            ${producto.descripcion}
        """.trimIndent()
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, textoCompartir)
            putExtra(Intent.EXTRA_SUBJECT, "Producto: ${producto.nombre}")
        }
        
        startActivity(Intent.createChooser(intent, "Compartir producto"))
    }
}
