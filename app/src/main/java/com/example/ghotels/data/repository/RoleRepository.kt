package com.example.ghotels.data.repository

import com.example.ghotels.data.model.RoleDto
import com.example.ghotels.data.source.remote.RoleServiceClient


class RoleRepository(
    private val serviceClient: RoleServiceClient
) {
    suspend fun getAll(): List<RoleDto> = serviceClient.getAll()

    suspend fun add(dto: RoleDto): Boolean = serviceClient.add(dto).isSuccessful

    suspend fun update(id: Long, dto: RoleDto): Boolean = serviceClient.update(id, dto).isSuccessful

    suspend fun delete(id: Long): Boolean = serviceClient.delete(id).isSuccessful
}


