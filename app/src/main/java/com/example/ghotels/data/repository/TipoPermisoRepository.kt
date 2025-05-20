package com.example.ghotels.data.repository

import com.example.ghotels.data.model.TipoPermisoDto
import com.example.ghotels.data.source.remote.TipoPermisoServiceClient

/**
 * Repositorio que gestiona los tipos de permisos disponibles.
 */
class TipoPermisoRepository(val service: TipoPermisoServiceClient) {


    suspend fun listarTodos(): List<TipoPermisoDto> = try {
        service.listarTodos()
    } catch (e: Exception) {
        emptyList()
    }

    suspend fun crear(dto: TipoPermisoDto): TipoPermisoDto? {
        val response = service.crear(dto)
        return if (response.isSuccessful) response.body() else null
    }
}

