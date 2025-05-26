package com.example.ghotels.domain.usecase

import com.example.ghotels.data.model.AsistenciaDto
import com.example.ghotels.data.repository.AsistenciaRepository

class AsistenciaUseCase(private val repository: AsistenciaRepository) {
    suspend fun registrarAsistencia(dto: AsistenciaDto): Result<Unit> = try {
        val response = repository.registrar(dto)
        if (response.isSuccessful) Result.success(Unit)
        else Result.failure(Exception("Error al registrar asistencia"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun obtenerAsistencias(empleadoId: Long): Result<List<AsistenciaDto>> = try {
        Result.success(repository.obtenerAsistenciasEmpleado(empleadoId))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
