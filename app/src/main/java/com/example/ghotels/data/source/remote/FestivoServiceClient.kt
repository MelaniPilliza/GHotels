package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.FestivoDto
import retrofit2.http.GET

interface FestivoServiceClient {
    @GET("api/festivos")
    suspend fun getFestivos(): List<FestivoDto>
}
