package com.example.ghotels.data.repository


import com.example.ghotels.data.model.DepartmentDto
import com.example.ghotels.data.source.remote.DepartmentServiceClient

/**
 * Repositorio para gestionar departamentos.
 */
class DepartmentRepository(
    private val serviceClient: DepartmentServiceClient
) {
    suspend fun getAll(): List<DepartmentDto> {
        return serviceClient.getAll()
    }

    suspend fun add(dto: DepartmentDto): Boolean {
        return serviceClient.add(dto).isSuccessful
    }

    suspend fun update(id: Long, dto: DepartmentDto): Boolean {
        return serviceClient.update(id, dto).isSuccessful
    }

    suspend fun delete(id: Long): Boolean {
        return serviceClient.delete(id).isSuccessful
    }
}