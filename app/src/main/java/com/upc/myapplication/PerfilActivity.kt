package com.upc.myapplication

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
        
        inicializarVistas()
        configurarToolbar()
        configurarBotones()
        cargarDatosUsuario()
    }
    
    private fun inicializarVistas() {
        botonEditarPerfil = findViewById(R.id.boton_editar_perfil)
        botonCerrarSesion = findViewById(R.id.boton_cerrar_sesion)
        textoNombreUsuario = findViewById(R.id.texto_nombre_usuario)
        textoEmailUsuario = findViewById(R.id.texto_email_usuario)
        textoTelefonoUsuario = findViewById(R.id.texto_telefono_usuario)
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
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    private fun cargarDatosUsuario() {
        // Aquí se cargarían los datos reales del usuario
        textoNombreUsuario.text = getString(R.string.nombre_usuario)
        textoEmailUsuario.text = getString(R.string.email_usuario)
        textoTelefonoUsuario.text = getString(R.string.telefono_usuario)
        textoDireccionUsuario.text = getString(R.string.direccion_usuario)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
