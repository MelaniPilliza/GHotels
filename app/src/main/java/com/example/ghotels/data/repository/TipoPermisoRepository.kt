package com.example.ghotels.data.repository

import com.example.ghotels.data.model.TipoPermisoDto
import com.example.ghotels.data.source.remote.TipoPermisoServiceClient

/**
 * Repositorio que gestiona los tipos de permisos disponibles.
 */
class TipoPermisoRepository(private val tipoPermisoServiceClient: TipoPermisoServiceClient) {
    suspend fun obtenerTiposPermiso(): List<TipoPermisoDto> {
        return tipoPermisoServiceClient.listarTodos()
    }
}


