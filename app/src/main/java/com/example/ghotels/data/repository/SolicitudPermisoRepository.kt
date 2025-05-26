package com.example.ghotels.data.repository

import com.example.ghotels.data.model.SolicitudPermisoDto
import com.example.ghotels.data.source.remote.SolicitudPermisoServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Repositorio que maneja las solicitudes de permisos.
 */
class SolicitudPermisoRepository(val solicitudServiceClient: SolicitudPermisoServiceClient) {


    fun observarPorEmpleado(empleadoId: Long): Flow<List<SolicitudPermisoDto>> =
        observeQuery(retryTime = 3000) {
            solicitudServiceClient.listarPorEmpleado(empleadoId)
        }


    suspend fun crear(dto: SolicitudPermisoDto): SolicitudPermisoDto? {
        val response = solicitudServiceClient.crear(dto)
        return if (response.isSuccessful) response.body() else null
    }


    suspend fun aprobar(id: Long): Boolean = solicitudServiceClient.aprobar(id).isSuccessful


    suspend fun rechazar(id: Long): Boolean = solicitudServiceClient.rechazar(id).isSuccessful

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

