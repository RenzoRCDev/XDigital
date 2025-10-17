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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.upc.myapplication.adaptadores.AdaptadorGaleriaImagenes
import com.upc.myapplication.databinding.ActivityDetallesProductoBinding
import com.upc.myapplication.datos.CarritoManager
import com.upc.myapplication.datos.RepositorioProductosAWS
import com.upc.myapplication.modelo.Producto
import com.upc.myapplication.modelo.ProductoImagen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallesProductoActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDetallesProductoBinding
    private lateinit var producto: Producto
    private lateinit var adaptadorGaleria: AdaptadorGaleriaImagenes
    private val repositorioAWS = RepositorioProductosAWS()
    private var imagenesGaleria = emptyList<ProductoImagen>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityDetallesProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Configurar la barra de estado
        window.statusBarColor = getColor(R.color.status_bar_color)
        
        CarritoManager.init(this)
        
        // Obtener el producto del intent
        producto = intent.getSerializableExtra("producto") as Producto
        
        configurarToolbar()
        mostrarDetallesProducto()
        configurarGaleria()
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
    
    private fun configurarGaleria() {
        // Configurar RecyclerView para la galería
        binding.recyclerGaleria.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        
        adaptadorGaleria = AdaptadorGaleriaImagenes(emptyList()) { imagen ->
            // Al hacer clic en una imagen, actualizar la imagen principal
            Glide.with(this)
                .load(imagen.urlImagen)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imagenProducto)
        }
        binding.recyclerGaleria.adapter = adaptadorGaleria
        
        // Cargar imágenes de la galería
        cargarImagenesGaleria()
    }
    
    private fun cargarImagenesGaleria() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val imagenes = repositorioAWS.obtenerImagenesProducto(producto.id)
                imagenesGaleria = imagenes
                
                // Actualizar adaptador
                adaptadorGaleria = AdaptadorGaleriaImagenes(imagenes) { imagen ->
                    // Al hacer clic en una imagen, actualizar la imagen principal
                    Glide.with(this@DetallesProductoActivity)
                        .load(imagen.urlImagen)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imagenProducto)
                }
                binding.recyclerGaleria.adapter = adaptadorGaleria
                
                // Mostrar u ocultar la galería según si hay imágenes
                if (imagenes.isNotEmpty()) {
                    binding.recyclerGaleria.visibility = View.VISIBLE
                } else {
                    binding.recyclerGaleria.visibility = View.GONE
                }
            } catch (e: Exception) {
                // En caso de error, ocultar la galería
                binding.recyclerGaleria.visibility = View.GONE
            }
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
