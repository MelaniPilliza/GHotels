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
class AsistenciaRepository(private val asistenciaServiceClient: AsistenciaServiceClient) {
    suspend fun obtenerAsistenciasEmpleado(id: Long) = asistenciaServiceClient.obtenerAsistencias(id)
    suspend fun registrar(asistencia: AsistenciaDto) = asistenciaServiceClient.registrarAsistencia(asistencia)
}