package com.upc.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class SoporteActivity : AppCompatActivity() {
    
    private lateinit var botonLlamar: MaterialButton
    private lateinit var botonEmail: MaterialButton
    private lateinit var botonWhatsapp: MaterialButton
    private lateinit var botonEnviarMensaje: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soporte)
        
        inicializarVistas()
        configurarToolbar()
        configurarBotones()
    }
    
    private fun inicializarVistas() {
        botonLlamar = findViewById(R.id.boton_llamar)
        botonEmail = findViewById(R.id.boton_email)
        botonWhatsapp = findViewById(R.id.boton_whatsapp)
        botonEnviarMensaje = findViewById(R.id.boton_enviar_mensaje)
    }
    
    private fun configurarToolbar() {
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.titulo_soporte)
    }
    
    private fun configurarBotones() {
        botonLlamar.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${getString(R.string.telefono_soporte)}")
            startActivity(intent)
        }
        
        botonEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${getString(R.string.email_soporte)}")
            startActivity(intent)
        }
        
        botonWhatsapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/51999999999")
            startActivity(intent)
        }
        
        botonEnviarMensaje.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de mensaje en desarrollo", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
