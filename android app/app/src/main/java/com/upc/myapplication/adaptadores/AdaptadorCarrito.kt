package com.upc.myapplication.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upc.myapplication.R
import com.upc.myapplication.databinding.ItemCarritoBinding
import com.upc.myapplication.modelo.CarritoItem

class AdaptadorCarrito(
    private var items: List<CarritoItem>,
    private val onCantidadCambiada: (CarritoItem, Int) -> Unit,
    private val onEliminarItem: (CarritoItem) -> Unit
) : RecyclerView.Adapter<AdaptadorCarrito.CarritoViewHolder>() {

    class CarritoViewHolder(val binding: ItemCarritoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val binding = ItemCarritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarritoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = items[position]
        
        holder.binding.nombreItem.text = item.producto.nombre
        holder.binding.precioItem.text = holder.itemView.context.getString(R.string.moneda) + 
            String.format("%.2f", item.obtenerPrecioTotal())
        holder.binding.cantidadItem.text = item.cantidad.toString()

        Glide.with(holder.itemView.context)
            .load(item.producto.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imagenItem)

        holder.binding.botonMenos.setOnClickListener {
            if (item.cantidad > 1) {
                onCantidadCambiada(item, item.cantidad - 1)
            }
        }

        holder.binding.botonMas.setOnClickListener {
            onCantidadCambiada(item, item.cantidad + 1)
        }

        holder.binding.botonEliminar.setOnClickListener {
            onEliminarItem(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun actualizarLista(nuevaLista: List<CarritoItem>) {
        items = nuevaLista
        notifyDataSetChanged()
    }
}