# X-Digital Imports - Tienda Virtual de Tecnología

Una aplicación móvil Android desarrollada en Kotlin para una tienda virtual de productos tecnológicos como celulares, cámaras, proyectores, laptops, audífonos y tablets.

## 🚀 Características

- **Catálogo de productos** con imágenes y detalles completos
- **Carrito de compras** persistente con SharedPreferences
- **Búsqueda de productos** en tiempo real
- **Filtrado por categorías** (Celulares, Cámaras, Proyectores, etc.)
- **Navegación inferior** con 4 secciones principales
- **Detalles de productos** con información completa
- **Interfaz moderna** con Material Design
- **Carga de imágenes** con Glide

## 📱 Pantallas

1. **Inicio**: Lista de productos con búsqueda y categorías
2. **Categorías**: Vista dedicada de categorías de productos
3. **Soporte**: Información de contacto y soporte
4. **Perfil**: Datos del usuario
5. **Detalles del Producto**: Información completa del producto seleccionado
6. **Carrito**: Gestión de productos agregados

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI**: Material Design Components
- **Arquitectura**: MVVM con ViewBinding
- **Navegación**: Bottom Navigation + Intents
- **Imágenes**: Glide
- **Persistencia**: SharedPreferences + Gson
- **Layout**: ConstraintLayout, LinearLayout, RecyclerView

## 📋 Requisitos del Sistema

- **Android Studio**: Arctic Fox o superior
- **SDK mínimo**: API 24 (Android 7.0)
- **SDK objetivo**: API 34 (Android 14)
- **Java**: JDK 11 o superior
- **Gradle**: 8.0 o superior

## 🔧 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/x-digital-imports.git
cd x-digital-imports
```

### 2. Abrir en Android Studio
1. Abre Android Studio
2. Selecciona "Open an existing project"
3. Navega a la carpeta del proyecto y ábrela

### 3. Configurar el proyecto
1. Android Studio detectará automáticamente el proyecto Gradle
2. Espera a que se descarguen las dependencias
3. Sincroniza el proyecto si es necesario

### 4. Ejecutar la aplicación
1. Conecta un dispositivo Android o inicia un emulador
2. Haz clic en el botón "Run" (▶️) o presiona Shift + F10
3. Selecciona tu dispositivo y espera a que se instale la app

## 📁 Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/upc/myapplication/
│   │   ├── MainActivity.kt                 # Actividad principal
│   │   ├── DetallesProductoActivity.kt    # Detalles del producto
│   │   ├── CarritoActivity.kt             # Carrito de compras
│   │   ├── CategoriasActivity.kt          # Categorías
│   │   ├── SoporteActivity.kt             # Soporte
│   │   ├── PerfilActivity.kt              # Perfil de usuario
│   │   ├── adaptadores/                   # Adaptadores de RecyclerView
│   │   ├── datos/                         # Repositorios y managers
│   │   └── modelo/                        # Modelos de datos
│   ├── res/                               # Recursos (layouts, strings, etc.)
│   └── AndroidManifest.xml               # Configuración de la app
├── build.gradle.kts                      # Configuración de Gradle
└── proguard-rules.pro                    # Reglas de ProGuard
```

## 🎨 Personalización

### Agregar nuevos productos
Edita el archivo `RepositorioProductos.kt` en la carpeta `datos/`:

```kotlin
Producto(
    id = "nuevo_id",
    nombre = "Nombre del Producto",
    descripcion = "Descripción del producto",
    precio = 999.99,
    imageUrl = "https://url-de-la-imagen.com/imagen.jpg",
    categoria = CategoriaProducto.CELULARES,
    marca = "Marca",
    stock = 10,
    calificacion = 4.5,
    caracteristicas = listOf("Característica 1", "Característica 2")
)
```

### Cambiar colores y estilos
Modifica los archivos en `res/values/`:
- `colors.xml` - Colores de la aplicación
- `themes.xml` - Temas y estilos
- `strings.xml` - Textos de la interfaz

## 🐛 Solución de Problemas

### Error de compilación
1. Verifica que tengas Java 11 o superior instalado
2. Limpia el proyecto: Build → Clean Project
3. Reconstruye el proyecto: Build → Rebuild Project

### Error de dependencias
1. Verifica tu conexión a internet
2. Sincroniza el proyecto: File → Sync Project with Gradle Files

### Error de dispositivo
1. Asegúrate de que el dispositivo tenga la depuración USB habilitada
2. Verifica que el dispositivo aparezca en Android Studio

## 📞 Soporte

Si encuentras algún problema o tienes preguntas:

1. Revisa la sección de [Issues](../../issues) del repositorio
2. Crea un nuevo issue describiendo el problema
3. Incluye capturas de pantalla si es necesario

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Contribuciones

Las contribuciones son bienvenidas. Para contribuir:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 🙏 Agradecimientos

- Material Design Components por Google
- Glide por Bumptech
- AndroidX por Google
- Comunidad de desarrolladores Android

---

**Desarrollado con ❤️ para la tienda X-Digital Imports**
