package com.upc.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.upc.myapplication.adaptadores.AdaptadorCategorias
import com.upc.myapplication.adaptadores.AdaptadorProductos
import com.upc.myapplication.datos.CarritoManager
import com.upc.myapplication.datos.RepositorioProductos
import com.upc.myapplication.datos.RepositorioProductosAWS
import com.upc.myapplication.modelo.CarritoItem
import com.upc.myapplication.modelo.CategoriaProducto
import com.upc.myapplication.modelo.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var recyclerCategorias: RecyclerView
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var campoBusqueda: TextInputEditText
    private lateinit var fabCarrito: FloatingActionButton
    private lateinit var contadorCarrito: android.widget.TextView
    private lateinit var bottomNavigation: BottomNavigationView
    
    private lateinit var adaptadorCategorias: AdaptadorCategorias
    private lateinit var adaptadorProductos: AdaptadorProductos
    
    // Repositorio para obtener datos de la API
    private val repositorioAWS = RepositorioProductosAWS()
    
    // El carrito ahora se maneja con CarritoManager
    private var productosActuales = emptyList<Producto>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Inicializar el CarritoManager
        CarritoManager.init(this)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        inicializarVistas()
        configurarRecyclerViews()
        configurarBusqueda()
        configurarCarrito()
        configurarNavegacionInferior()
        cargarDatosIniciales()
    }
    
    private fun inicializarVistas() {
        recyclerCategorias = findViewById(R.id.recycler_categorias)
        recyclerProductos = findViewById(R.id.recycler_productos)
        campoBusqueda = findViewById(R.id.campo_busqueda)
        fabCarrito = findViewById(R.id.fab_carrito)
        contadorCarrito = findViewById(R.id.contador_carrito)
        bottomNavigation = findViewById(R.id.bottom_navigation)
    }
    
    private fun configurarRecyclerViews() {
        // Configurar RecyclerView de categorías (se cargará desde la API)
        adaptadorCategorias = AdaptadorCategorias(
            emptyList()
        ) { categoria ->
            filtrarPorCategoria(categoria)
        }
        recyclerCategorias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerCategorias.adapter = adaptadorCategorias
        
        // Configurar RecyclerView de productos
        adaptadorProductos = AdaptadorProductos(
            productosActuales,
            onProductoClick = { producto ->
                mostrarDetallesProducto(producto)
            },
            onAgregarCarritoClick = { producto ->
                agregarAlCarrito(producto)
            }
        )
        recyclerProductos.layoutManager = GridLayoutManager(this, 2)
        recyclerProductos.adapter = adaptadorProductos
        
        // Actualizar contador del carrito
        actualizarContadorCarrito()
    }
    
    private fun configurarBusqueda() {
        campoBusqueda.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val consulta = s.toString().trim()
                if (consulta.isEmpty()) {
                    mostrarTodosLosProductos()
                } else {
                    buscarProductos(consulta)
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun configurarCarrito() {
        fabCarrito.setOnClickListener {
            mostrarCarrito()
        }
    }
    
    private fun configurarNavegacionInferior() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    // Ya estamos en la pantalla de inicio
                    true
                }
                R.id.nav_categorias -> {
                    mostrarCategorias()
                    true
                }
                R.id.nav_soporte -> {
                    mostrarSoporte()
                    true
                }
                R.id.nav_perfil -> {
                    mostrarPerfil()
                    true
                }
                else -> false
            }
        }
    }
    
    private fun cargarDatosIniciales() {
        cargarCategorias()
        
        // Verificar si se seleccionó una categoría específica
        val categoriaSeleccionada = intent.getStringExtra("categoria_seleccionada")
        if (categoriaSeleccionada != null) {
            try {
                val categoria = CategoriaProducto.valueOf(categoriaSeleccionada)
                filtrarPorCategoria(categoria)
            } catch (e: Exception) {
                mostrarTodosLosProductos()
            }
        } else {
            mostrarTodosLosProductos()
        }
        
        actualizarContadorCarrito()
    }
    
    private fun cargarCategorias() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val categorias = repositorioAWS.obtenerTodasLasCategorias()
                adaptadorCategorias = AdaptadorCategorias(
                    categorias
                ) { categoria ->
                    filtrarPorCategoria(categoria)
                }
                recyclerCategorias.adapter = adaptadorCategorias
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cargar categorías: ${e.message}", Toast.LENGTH_SHORT).show()
                // Usar categorías por defecto en caso de error
                val categoriasPorDefecto = CategoriaProducto.values().toList()
                adaptadorCategorias = AdaptadorCategorias(
                    categoriasPorDefecto
                ) { categoria ->
                    filtrarPorCategoria(categoria)
                }
                recyclerCategorias.adapter = adaptadorCategorias
            }
        }
    }
    
    private fun mostrarTodosLosProductos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                productosActuales = repositorioAWS.obtenerTodosLosProductos()
                actualizarAdaptadorProductos()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cargar productos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun filtrarPorCategoria(categoria: CategoriaProducto) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (categoria == CategoriaProducto.TODOS) {
                    productosActuales = repositorioAWS.obtenerTodosLosProductos()
                } else {
                    productosActuales = repositorioAWS.obtenerProductosPorCategoria(categoria)
                }
                actualizarAdaptadorProductos()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al filtrar productos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun buscarProductos(consulta: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                android.util.Log.d("MainActivity", "Buscando productos con consulta: '$consulta'")
                productosActuales = repositorioAWS.buscarProductos(consulta)
                android.util.Log.d("MainActivity", "Productos encontrados: ${productosActuales.size}")
                actualizarAdaptadorProductos()
            } catch (e: Exception) {
                android.util.Log.e("MainActivity", "Error al buscar productos", e)
                Toast.makeText(this@MainActivity, "Error al buscar productos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun actualizarAdaptadorProductos() {
        adaptadorProductos = AdaptadorProductos(
            productosActuales,
            onProductoClick = { producto ->
                mostrarDetallesProducto(producto)
            },
            onAgregarCarritoClick = { producto ->
                agregarAlCarrito(producto)
            }
        )
        recyclerProductos.adapter = adaptadorProductos
    }
    
    private fun agregarAlCarrito(producto: Producto) {
        CarritoManager.agregarProducto(producto)
        actualizarContadorCarrito()
        Toast.makeText(this, getString(R.string.producto_agregado), Toast.LENGTH_SHORT).show()
    }
    
    private fun actualizarContadorCarrito() {
        val totalItems = CarritoManager.obtenerCantidadTotal()
        if (totalItems > 0) {
            contadorCarrito.text = totalItems.toString()
            contadorCarrito.visibility = android.view.View.VISIBLE
        } else {
            contadorCarrito.visibility = android.view.View.GONE
        }
    }
    
    private fun mostrarDetallesProducto(producto: Producto) {
        val intent = Intent(this, DetallesProductoActivity::class.java)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }
    
    private fun mostrarCarrito() {
        val intent = android.content.Intent(this, CarritoActivity::class.java)
        startActivity(intent)
    }
    
    private fun mostrarCategorias() {
        val intent = android.content.Intent(this, CategoriasActivity::class.java)
        startActivity(intent)
    }
    
    private fun mostrarSoporte() {
        val intent = android.content.Intent(this, SoporteActivity::class.java)
        startActivity(intent)
    }
    
    private fun mostrarPerfil() {
        val intent = android.content.Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }
}