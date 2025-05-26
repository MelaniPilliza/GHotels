package com.example.ghotels.data.repository


import com.example.ghotels.data.source.remote.DepartamentoServiceClient
import com.example.ghotels.domain.model.Departamento

/**
 * Repositorio para gestionar departamentos.
 */
class DepartamentoRepository(val departamentoServiceClient: DepartamentoServiceClient) {


    suspend fun listarTodos(): List<Departamento> = try {
        departamentoServiceClient.listarTodos()
    } catch (e: Exception) {
        emptyList()
    }

    suspend fun crear(departamento: Departamento): Departamento? {
        val response = departamentoServiceClient.crear(departamento)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun obtenerPorNombre(nombre: String): Departamento? {
        val response = departamentoServiceClient.obtenerPorNombre(nombre)
        return if (response.isSuccessful) response.body() else null
    }
}
