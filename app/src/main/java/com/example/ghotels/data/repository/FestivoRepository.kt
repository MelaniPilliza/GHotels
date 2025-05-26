package com.example.ghotels.data.repository

import com.example.ghotels.data.model.FestivoDto
import com.example.ghotels.data.source.remote.FestivoServiceClient

class FestivoRepository(private val festivoServiceClient: FestivoServiceClient) {
    suspend fun obtenerFestivos(): List<FestivoDto> = festivoServiceClient.getFestivos()
}
