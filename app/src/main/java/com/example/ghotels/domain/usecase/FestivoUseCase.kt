package com.example.ghotels.domain.usecase

import com.example.ghotels.data.model.FestivoDto
import com.example.ghotels.data.repository.FestivoRepository

class FestivoUseCase(private val repository: FestivoRepository) {
    suspend operator fun invoke(): Result<List<FestivoDto>> = try {
        Result.success(repository.obtenerFestivos())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
