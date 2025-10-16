package com.upc.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.upc.myapplication.adaptadores.AdaptadorCategorias
import com.upc.myapplication.modelo.CategoriaProducto

class CategoriasActivity : AppCompatActivity() {
    
    private lateinit var adaptadorCategorias: AdaptadorCategorias
    private lateinit var recyclerCategorias: RecyclerView
    
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
        adaptadorCategorias = AdaptadorCategorias(
            CategoriaProducto.values().toList()
        ) { categoria ->
            // Regresar a la pantalla principal con la categoría seleccionada
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("categoria_seleccionada", categoria.name)
            startActivity(intent)
            finish()
        }
        
        recyclerCategorias.layoutManager = GridLayoutManager(this, 2)
        recyclerCategorias.adapter = adaptadorCategorias
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return true
    }
}
