package com.upc.myapplication.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "https://fsh5qm7kb8.execute-api.us-east-1.amazonaws.com/v1/"

object ApiClient{
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface ApiService{
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse>
}