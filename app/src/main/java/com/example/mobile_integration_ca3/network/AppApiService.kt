package com.example.mobile_integration_ca3.network

import com.example.mobile_integration_ca3.model.Response
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.0.103:4000/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface AppApiService {
    @GET("users")
    suspend fun getUsers(): Response
}
object AppApi {
    val retrofitService : AppApiService by lazy {
        retrofit.create(AppApiService::class.java)
    }
}

