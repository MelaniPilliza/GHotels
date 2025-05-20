package com.example.ghotels.data.repository

import com.example.ghotels.data.source.remote.RolServiceClient
import com.example.ghotels.domain.model.Rol

/**
 * Repositorio que gestiona los roles disponibles en la empresa.
 */
class RolRepository(val service: RolServiceClient) {


    suspend fun listarTodos(): List<Rol> = try {
        service.listarTodos()
    } catch (e: Exception) {
        emptyList()
    }

    suspend fun obtenerPorNombre(nombre: String): Rol? {
        val response = service.obtenerPorNombre(nombre)
        return if (response.isSuccessful) response.body() else null
    }
}
