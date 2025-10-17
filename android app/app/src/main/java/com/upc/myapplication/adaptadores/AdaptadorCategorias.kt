package com.upc.myapplication.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upc.myapplication.databinding.ItemCategoriaBinding
import com.upc.myapplication.modelo.CategoriaProducto

class AdaptadorCategorias(
    private val categorias: List<CategoriaProducto>,
    private val onCategoriaClick: (CategoriaProducto) -> Unit
) : RecyclerView.Adapter<AdaptadorCategorias.CategoriaViewHolder>() {

    // Agregar "Todos" al inicio de la lista
    private val categoriasConTodos = listOf(CategoriaProducto.TODOS) + categorias

    class CategoriaViewHolder(val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = ItemCategoriaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categoriasConTodos[position]
        holder.binding.nombreCategoria.text = categoria.nombre
        
        holder.itemView.setOnClickListener {
            onCategoriaClick(categoria)
        }
    }

    override fun getItemCount(): Int = categoriasConTodos.size
}
