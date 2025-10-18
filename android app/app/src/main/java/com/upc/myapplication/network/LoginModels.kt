package com.upc.myapplication.network

data class LoginRequest(
    val user : String,
    val pwd : String
)

data class ApiResponse(
    val statusCode: Int,
    // El campo "body" es un String que contiene JSON, lo necesitamos como String.
    val body: String
)

data class BodyData(
    // El campo "data" es una lista de usuarios
    val data: List<UserData>
)
data class UserData(
    val idCliente: Int,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val telefono: String,
    val nombrescompletos: String
)