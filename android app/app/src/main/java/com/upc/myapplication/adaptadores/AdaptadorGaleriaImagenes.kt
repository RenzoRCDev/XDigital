package com.upc.myapplication.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upc.myapplication.R
import com.upc.myapplication.modelo.ProductoImagen

class AdaptadorGaleriaImagenes(
    private val imagenes: List<ProductoImagen>,
    private val onImagenClick: (ProductoImagen) -> Unit
) : RecyclerView.Adapter<AdaptadorGaleriaImagenes.ImagenViewHolder>() {

    class ImagenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenView: ImageView = itemView.findViewById(R.id.imagen_galeria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imagen_galeria, parent, false)
        return ImagenViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagenViewHolder, position: Int) {
        val imagen = imagenes[position]
        
        Glide.with(holder.itemView.context)
            .load(imagen.urlImagen)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.imagenView)
        
        holder.itemView.setOnClickListener {
            onImagenClick(imagen)
        }
    }

    override fun getItemCount(): Int = imagenes.size
}
