package com.upc.myapplication.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upc.myapplication.databinding.ItemProductoBinding
import com.upc.myapplication.modelo.Producto

class AdaptadorProductos(
    private val productos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit,
    private val onAgregarCarritoClick: (Producto) -> Unit
) : RecyclerView.Adapter<AdaptadorProductos.ProductoViewHolder>() {

    class ProductoViewHolder(val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        
        // Cargar imagen con Glide
        Glide.with(holder.itemView.context)
            .load(producto.imageUrl)
            .placeholder(com.upc.myapplication.R.drawable.ic_launcher_foreground)
            .error(com.upc.myapplication.R.drawable.ic_launcher_foreground)
            .into(holder.binding.imagenProducto)
        
        holder.binding.nombreProducto.text = producto.nombre
        holder.binding.precioProducto.text = "${holder.itemView.context.getString(com.upc.myapplication.R.string.moneda)} ${String.format("%.2f", producto.precio)}"
        holder.binding.marcaProducto.text = producto.marca
        holder.binding.calificacionProducto.text = "★ ${producto.calificacion}"
        holder.binding.stockProducto.text = "${holder.itemView.context.getString(com.upc.myapplication.R.string.stock)}: ${producto.stock}"
        
        // Configurar click en el producto
        holder.itemView.setOnClickListener {
            onProductoClick(producto)
        }
        
        // Configurar click en agregar al carrito
        holder.binding.botonAgregarCarrito.setOnClickListener {
            onAgregarCarritoClick(producto)
        }
    }

    override fun getItemCount(): Int = productos.size
}
