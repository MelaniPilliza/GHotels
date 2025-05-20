package com.example.ghotels.data.repository

import com.example.ghotels.data.model.AsistenciaDto
import com.example.ghotels.data.source.remote.AsistenciaServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Repositorio para manejar registros y consultas de asistencias.
 */
class AsistenciaRepository(val service: AsistenciaServiceClient) {


    fun observarPorEmpleado(empleadoId: Long): Flow<List<AsistenciaDto>> =
        observeQuery(retryTime = 3000) {
            service.getPorEmpleado(empleadoId)
        }

    suspend fun registrarAsistencia(dto: AsistenciaDto): AsistenciaDto? {
        val response = service.crear(dto)
        return if (response.isSuccessful) response.body() else null
    }

    private fun <T> observeQuery(retryTime: Long = 5000, query: suspend () -> List<T>): Flow<List<T>> = flow {
        var lastResult: List<T> = emptyList()
        while (true) {
            try {
                val newResult = query()
                if (newResult != lastResult) {
                    lastResult = newResult
                    emit(newResult)
                }
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.message}")
            }
            delay(retryTime)
        }
    }.flowOn(Dispatchers.IO)
}