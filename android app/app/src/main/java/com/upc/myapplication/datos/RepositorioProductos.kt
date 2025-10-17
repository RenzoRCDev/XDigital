package com.upc.myapplication.datos

import com.upc.myapplication.modelo.CategoriaProducto
import com.upc.myapplication.modelo.Producto

object RepositorioProductos {
    private val productos = listOf(
        // Celulares
        Producto(
            id = "1",
            nombre = "iPhone 15 Pro Max",
            descripcion = "El smartphone más avanzado de Apple con chip A17 Pro y cámara de 48MP",
            precio = 1299.99,
            imageUrl = "https://via.placeholder.com/300x300?text=iPhone+15+Pro+Max",
            categoria = CategoriaProducto.CELULARES,
            marca = "Apple",
            stock = 15,
            calificacion = 4.8,
            caracteristicas = listOf("Pantalla 6.7 pulgadas", "128GB almacenamiento", "Cámara 48MP", "5G")
        ),
        Producto(
            id = "2",
            nombre = "Samsung Galaxy S24 Ultra",
            descripcion = "Smartphone premium con S Pen y cámara de 200MP",
            precio = 1199.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Galaxy+S24+Ultra",
            categoria = CategoriaProducto.CELULARES,
            marca = "Samsung",
            stock = 12,
            calificacion = 4.7,
            caracteristicas = listOf("Pantalla 6.8 pulgadas", "256GB almacenamiento", "S Pen incluido", "5G")
        ),
        Producto(
            id = "3",
            nombre = "Google Pixel 8 Pro",
            descripcion = "Smartphone con la mejor cámara y inteligencia artificial de Google",
            precio = 999.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Pixel+8+Pro",
            categoria = CategoriaProducto.CELULARES,
            marca = "Google",
            stock = 8,
            calificacion = 4.6,
            caracteristicas = listOf("Pantalla 6.7 pulgadas", "128GB almacenamiento", "Cámara 50MP", "Android 14")
        ),
        
        // Cámaras
        Producto(
            id = "4",
            nombre = "Canon EOS R5",
            descripcion = "Cámara mirrorless profesional con grabación 8K",
            precio = 3899.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Canon+EOS+R5",
            categoria = CategoriaProducto.CAMARAS,
            marca = "Canon",
            stock = 5,
            calificacion = 4.9,
            caracteristicas = listOf("Sensor 45MP", "Grabación 8K", "Estabilización 5 ejes", "WiFi integrado")
        ),
        Producto(
            id = "5",
            nombre = "Sony A7 IV",
            descripcion = "Cámara full-frame con excelente rendimiento en poca luz",
            precio = 2499.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Sony+A7+IV",
            categoria = CategoriaProducto.CAMARAS,
            marca = "Sony",
            stock = 7,
            calificacion = 4.8,
            caracteristicas = listOf("Sensor 33MP", "Grabación 4K", "Estabilización 5 ejes", "Pantalla táctil")
        ),
        Producto(
            id = "6",
            nombre = "Nikon Z9",
            descripcion = "Cámara profesional con grabación 8K y autofoco avanzado",
            precio = 5499.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Nikon+Z9",
            categoria = CategoriaProducto.CAMARAS,
            marca = "Nikon",
            stock = 3,
            calificacion = 4.9,
            caracteristicas = listOf("Sensor 45MP", "Grabación 8K", "120fps en 4K", "Resistente al agua")
        ),
        
        // Proyectores
        Producto(
            id = "7",
            nombre = "Epson PowerLite 1781W",
            descripcion = "Proyector portátil con resolución WXGA y 3200 lúmenes",
            precio = 599.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Epson+PowerLite+1781W",
            categoria = CategoriaProducto.PROYECTORES,
            marca = "Epson",
            stock = 10,
            calificacion = 4.5,
            caracteristicas = listOf("Resolución WXGA", "3200 lúmenes", "Conexión WiFi", "Portátil")
        ),
        Producto(
            id = "8",
            nombre = "BenQ HT2050A",
            descripcion = "Proyector para cine en casa con resolución Full HD",
            precio = 799.99,
            imageUrl = "https://via.placeholder.com/300x300?text=BenQ+HT2050A",
            categoria = CategoriaProducto.PROYECTORES,
            marca = "BenQ",
            stock = 6,
            calificacion = 4.7,
            caracteristicas = listOf("Resolución Full HD", "2200 lúmenes", "Contraste 15000:1", "HDR")
        ),
        Producto(
            id = "9",
            nombre = "Optoma UHD50X",
            descripcion = "Proyector 4K UHD con HDR y 2400 lúmenes",
            precio = 1299.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Optoma+UHD50X",
            categoria = CategoriaProducto.PROYECTORES,
            marca = "Optoma",
            stock = 4,
            calificacion = 4.8,
            caracteristicas = listOf("Resolución 4K UHD", "2400 lúmenes", "HDR10", "Gaming 240Hz")
        ),
        
        // Laptops
        Producto(
            id = "10",
            nombre = "MacBook Pro 16 pulgadas",
            descripcion = "Laptop profesional con chip M3 Pro y pantalla Liquid Retina XDR",
            precio = 2499.99,
            imageUrl = "https://via.placeholder.com/300x300?text=MacBook+Pro+16",
            categoria = CategoriaProducto.LAPTOPS,
            marca = "Apple",
            stock = 8,
            calificacion = 4.9,
            caracteristicas = listOf("Chip M3 Pro", "16GB RAM", "512GB SSD", "Pantalla 16 pulgadas")
        ),
        Producto(
            id = "11",
            nombre = "Dell XPS 15",
            descripcion = "Laptop premium con pantalla 4K y procesador Intel i7",
            precio = 1899.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Dell+XPS+15",
            categoria = CategoriaProducto.LAPTOPS,
            marca = "Dell",
            stock = 12,
            calificacion = 4.6,
            caracteristicas = listOf("Intel i7-13700H", "16GB RAM", "512GB SSD", "Pantalla 4K 15.6 pulgadas")
        ),
        
        // Audífonos
        Producto(
            id = "12",
            nombre = "Sony WH-1000XM5",
            descripcion = "Audífonos inalámbricos con cancelación de ruido líder en la industria",
            precio = 399.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Sony+WH-1000XM5",
            categoria = CategoriaProducto.AUDIFONOS,
            marca = "Sony",
            stock = 20,
            calificacion = 4.8,
            caracteristicas = listOf("Cancelación de ruido", "30 horas batería", "Carga rápida", "Bluetooth 5.2")
        ),
        Producto(
            id = "13",
            nombre = "AirPods Pro 2",
            descripcion = "Audífonos inalámbricos con cancelación de ruido adaptativa",
            precio = 249.99,
            imageUrl = "https://via.placeholder.com/300x300?text=AirPods+Pro+2",
            categoria = CategoriaProducto.AUDIFONOS,
            marca = "Apple",
            stock = 25,
            calificacion = 4.7,
            caracteristicas = listOf("Cancelación de ruido", "6 horas batería", "Carga inalámbrica", "Resistente al agua")
        ),
        
        // Tablets
        Producto(
            id = "14",
            nombre = "iPad Pro 12.9 pulgadas",
            descripcion = "Tablet profesional con chip M2 y pantalla Liquid Retina XDR",
            precio = 1099.99,
            imageUrl = "https://via.placeholder.com/300x300?text=iPad+Pro+12.9",
            categoria = CategoriaProducto.TABLETS,
            marca = "Apple",
            stock = 15,
            calificacion = 4.8,
            caracteristicas = listOf("Chip M2", "128GB almacenamiento", "Pantalla 12.9 pulgadas", "Soporte Apple Pencil")
        ),
        Producto(
            id = "15",
            nombre = "Samsung Galaxy Tab S9 Ultra",
            descripcion = "Tablet Android premium con pantalla de 14.6 pulgadas",
            precio = 1199.99,
            imageUrl = "https://via.placeholder.com/300x300?text=Galaxy+Tab+S9+Ultra",
            categoria = CategoriaProducto.TABLETS,
            marca = "Samsung",
            stock = 8,
            calificacion = 4.6,
            caracteristicas = listOf("Snapdragon 8 Gen 2", "256GB almacenamiento", "Pantalla 14.6 pulgadas", "S Pen incluido")
        )
    )
    
    fun obtenerTodosLosProductos(): List<Producto> = productos
    
    fun obtenerProductosPorCategoria(categoria: CategoriaProducto): List<Producto> = 
        productos.filter { it.categoria == categoria }
    
    fun buscarProductos(consulta: String): List<Producto> = 
        productos.filter { 
            it.nombre.contains(consulta, ignoreCase = true) ||
            it.descripcion.contains(consulta, ignoreCase = true) ||
            it.marca.contains(consulta, ignoreCase = true)
        }
    
    fun obtenerProductoPorId(id: String): Producto? = productos.find { it.id == id }
}
