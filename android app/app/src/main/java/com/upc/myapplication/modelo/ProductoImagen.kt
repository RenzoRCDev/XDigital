package com.upc.myapplication.modelo

import com.google.gson.annotations.SerializedName

data class ProductoImagen(
    @SerializedName("idimagen")
    val idImagen: Int,
    
    @SerializedName("urlimagen")
    val urlImagen: String
)
