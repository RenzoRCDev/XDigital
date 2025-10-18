package com.upc.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.upc.myapplication.databinding.ActivityLoginBinding
import com.upc.myapplication.network.ApiClient
import com.upc.myapplication.network.ApiService
import com.upc.myapplication.network.BodyData
import com.upc.myapplication.network.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SessionManager.init(this)
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */


        iniciaFuncionalidades()
    }

    private fun iniciaFuncionalidades() {
        binding.btnRegresar.setOnClickListener {
            finish()
        }

        binding.btnIngresar.setOnClickListener {
            //dejamos vacios los campos de alerta
            binding.txtMsjUsuario.text = ""
            binding.txtMsjPwd.text = ""

            val user = binding.txtUser.text.toString()
            val pass = binding.txtPwd.text.toString()

            if (user.isBlank()) {
                binding.txtMsjUsuario.text = "Este campo no puede estar vacío"
                //detener ejecución
                return@setOnClickListener
            }

            if (pass.isBlank()) {
                binding.txtMsjPwd.text = "Este campo no puede estar vacío"
                //detener ejecución
                return@setOnClickListener
            }

            realizaLogin(user,pass)
        }
    }

    private fun realizaLogin(usuario: String, password: String) {
        // Inicia una corutina en un hilo secundario para operaciones de red
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val loginRequest = LoginRequest(usuario, password)
                val response = apiService.login(loginRequest)

                // Cambia al hilo principal para manejar la respuesta y actualizar la UI
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("API_SUCCESS", "Respuesta del servidor: $apiResponse")

                        if (apiResponse != null) {
                            try {
                                // Parsea manualmente el String 'body' que contiene el JSON real
                                val gson = Gson()
                                val bodyData = gson.fromJson(apiResponse.body, BodyData::class.java)

                                // Comprueba si la lista de datos del usuario no está vacía
                                if (bodyData.data.isNotEmpty()) {
                                    // Al estar 'data' definida como List<UserData>, 'userData' será de tipo UserData
                                    val userData = bodyData.data[0]

                                    // Ahora el compilador sabe que 'userData' tiene una propiedad 'nombres'
                                    if (!userData.nombres.isNullOrBlank()) {
                                        // ¡ÉXITO!
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "¡Bienvenido ${userData.nombres}!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        //Guardar la sesion
                                        SessionManager.saveSession(userData)

                                        // navegar a la siguiente pantalla
                                        val intent = Intent(this@LoginActivity, PerfilActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        binding.txtMsjUsuario.text = "Respuesta de servidor inválida"
                                    }
                                } else {
                                    // Credenciales incorrectas (la API devolvió 200 OK pero sin datos)
                                    binding.txtMsjUsuario.text = "Usuario o contraseña incorrectos"
                                }
                            } catch (e: Exception) {
                                // El String 'body' no pudo ser parseado a JSON
                                binding.txtMsjUsuario.text = "Error al procesar la respuesta del servidor"
                                Log.e("API_PARSE_ERROR", "No se pudo parsear el JSON del body", e)
                            }
                        } else {
                            binding.txtMsjUsuario.text = "Respuesta del servidor vacía"
                        }
                    } else {
                        // La API respondió con un error HTTP (ej. 401, 500)
                        val errorBody = response.errorBody()?.string()
                        binding.txtMsjUsuario.text = "Error de servidor: ${response.code()}"
                        Log.e("API_HTTP_ERROR", "Código: ${response.code()}, Cuerpo: $errorBody")
                    }
                }
            } catch (e: Exception) {
                // Error de red (sin conexión, etc.)
                withContext(Dispatchers.Main) {
                    binding.txtMsjUsuario.text = "Error de conexión. Revisa tu internet."
                    Log.e("API_CONNECTION_ERROR", "Fallo la llamada de red", e)
                }
            }
        }
    }
}