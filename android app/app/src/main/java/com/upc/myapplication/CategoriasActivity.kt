package com.upc.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.upc.myapplication.adaptadores.AdaptadorCategorias
import com.upc.myapplication.datos.RepositorioProductosAWS
import com.upc.myapplication.modelo.CategoriaProducto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriasActivity : AppCompatActivity() {
    
    private lateinit var adaptadorCategorias: AdaptadorCategorias
    private lateinit var recyclerCategorias: RecyclerView
    private val repositorioAWS = RepositorioProductosAWS()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)
        
        inicializarVistas()
        configurarToolbar()
        configurarRecyclerView()
    }
    
    private fun inicializarVistas() {
        recyclerCategorias = findViewById(R.id.recycler_categorias)
    }
    
    private fun configurarToolbar() {
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.titulo_categorias)
    }
    
    private fun configurarRecyclerView() {
        // Cargar categorías desde la API
        cargarCategorias()
    }
    
    private fun cargarCategorias() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val categorias = repositorioAWS.obtenerTodasLasCategorias()
                adaptadorCategorias = AdaptadorCategorias(
                    categorias
                ) { categoria ->
                    // Regresar a la pantalla principal con la categoría seleccionada
                    val intent = Intent(this@CategoriasActivity, MainActivity::class.java)
                    intent.putExtra("categoria_seleccionada", categoria.name)
                    startActivity(intent)
                    finish()
                }
                
                recyclerCategorias.layoutManager = GridLayoutManager(this@CategoriasActivity, 2)
                recyclerCategorias.adapter = adaptadorCategorias
            } catch (e: Exception) {
                Toast.makeText(this@CategoriasActivity, "Error al cargar categorías: ${e.message}", Toast.LENGTH_SHORT).show()
                // Usar categorías por defecto en caso de error
                val categoriasPorDefecto = CategoriaProducto.values().toList()
                adaptadorCategorias = AdaptadorCategorias(
                    categoriasPorDefecto
                ) { categoria ->
                    val intent = Intent(this@CategoriasActivity, MainActivity::class.java)
                    intent.putExtra("categoria_seleccionada", categoria.name)
                    startActivity(intent)
                    finish()
                }
                
                recyclerCategorias.layoutManager = GridLayoutManager(this@CategoriasActivity, 2)
                recyclerCategorias.adapter = adaptadorCategorias
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return true
    }
}
