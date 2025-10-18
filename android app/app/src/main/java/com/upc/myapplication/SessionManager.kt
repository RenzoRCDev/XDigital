package com.upc.myapplication

import android.content.Context
import android.content.SharedPreferences
import com.upc.myapplication.network.UserData // <-- Importa tu data class

object SessionManager {    private const val PREFS_NAME = "AppSessionPrefs"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_ID_CLIENTE = "idCliente"
    private const val KEY_NOMBRES = "nombres"
    private const val KEY_APELLIDOS = "apellidos"
    private const val KEY_EMAIL = "email"
    private const val KEY_TELEFONO = "telefono"
    private const val KEY_NOMBRES_COMPLETOS = "nombresCompletos"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // (MODIFICADO) Ahora guarda el objeto UserData completo
    fun saveSession(userData: UserData) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_ID_CLIENTE, userData.idCliente)
        editor.putString(KEY_NOMBRES, userData.nombres)
        editor.putString(KEY_APELLIDOS, userData.apellidos)
        editor.putString(KEY_EMAIL, userData.email)
        editor.putString(KEY_TELEFONO, userData.telefono)
        editor.putString(KEY_NOMBRES_COMPLETOS, userData.nombrescompletos)
        editor.apply()
    }

    // (NUEVO) Recupera todos los datos del usuario como un objeto UserData
    fun getUserData(): UserData? {
        if (!isLoggedIn()) return null

        return UserData(
            idCliente = prefs.getInt(KEY_ID_CLIENTE, -1),
            nombres = prefs.getString(KEY_NOMBRES, "") ?: "",
            apellidos = prefs.getString(KEY_APELLIDOS, "") ?: "",
            email = prefs.getString(KEY_EMAIL, "") ?: "",
            telefono = prefs.getString(KEY_TELEFONO, "") ?: "",
            nombrescompletos = prefs.getString(KEY_NOMBRES_COMPLETOS, "") ?: ""
        )
    }

    // (NUEVO) Cierra la sesión y borra todos los datos
    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}
