package com.upc.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class PerfilActivity : AppCompatActivity() {
    
    private lateinit var botonEditarPerfil: MaterialButton
    private lateinit var botonCerrarSesion: MaterialButton
    private lateinit var textoNombreUsuario: android.widget.TextView
    private lateinit var textoEmailUsuario: android.widget.TextView
    private lateinit var textoTelefonoUsuario: android.widget.TextView
    private lateinit var textoDireccionUsuario: android.widget.TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        SessionManager.init(this)

        inicializarVistas()
        configurarToolbar()
        configurarBotones()
        cargarDatosUsuario()
    }
    
    private fun inicializarVistas() {
        botonEditarPerfil = findViewById(R.id.boton_editar_perfil)
        botonCerrarSesion = findViewById(R.id.boton_cerrar_sesion)
        textoNombreUsuario = findViewById(R.id.txtNombreUsuario)
        textoEmailUsuario = findViewById(R.id.txtEmailUsuario)
        textoTelefonoUsuario = findViewById(R.id.txtTelefonoUsuario)
        textoDireccionUsuario = findViewById(R.id.texto_direccion_usuario)
    }
    
    private fun configurarToolbar() {
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.titulo_perfil)
    }
    
    private fun configurarBotones() {
        botonEditarPerfil.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de editar perfil en desarrollo", Toast.LENGTH_SHORT).show()
        }
        
        botonCerrarSesion.setOnClickListener {
            cerrarSesionCompleto()
        }
    }

    private fun cargarDatosUsuario() {
        // 1. Recupera el objeto UserData del SessionManager
        val userData = SessionManager.getUserData()

        // 2. Comprueba si los datos existen
        if (userData != null) {
            // 3. Asigna los datos reales a los TextViews
            textoNombreUsuario.text = userData.nombrescompletos
            textoEmailUsuario.text = userData.email
            textoTelefonoUsuario.text = userData.telefono

            // Nota: Tu API no parece devolver una dirección, así que este campo podría quedar vacío o podrías ocultarlo.
            // textoDireccionUsuario.text = ""
        } else {
            // Si por alguna razón no se encuentran datos, muestra un mensaje de error.
            textoNombreUsuario.text = "Error al cargar el usuario"
            textoEmailUsuario.text = ""
            textoTelefonoUsuario.text = ""
            // textoDireccionUsuario.text = ""
        }
    }

    // --- NUEVA FUNCIÓN PARA UN CIERRE DE SESIÓN COMPLETO ---
    private fun cerrarSesionCompleto() {
        // 1. Limpia los datos guardados en SharedPreferences
        SessionManager.clearSession()

        // 2. Muestra un mensaje al usuario
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        // 3. Redirige a la pantalla principal y limpia la pila de actividades
        //    para que el usuario no pueda "volver" a la pantalla de perfil.
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Cierra PerfilActivity
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
