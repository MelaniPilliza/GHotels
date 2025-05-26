package com.example.ghotels.domain.usecase

import com.example.ghotels.data.model.TipoPermisoDto
import com.example.ghotels.data.repository.TipoPermisoRepository

class TipoPermisoUseCase(private val repository: TipoPermisoRepository) {
    suspend operator fun invoke(): Result<List<TipoPermisoDto>> = try {
        Result.success(repository.obtenerTiposPermiso())
    } catch (e: Exception) {
        Result.failure(e)
    }
}