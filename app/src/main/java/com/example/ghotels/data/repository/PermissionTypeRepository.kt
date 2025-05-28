package com.example.ghotels.data.repository

import com.example.ghotels.data.model.PermissionTypeDto
import com.example.ghotels.data.source.remote.PermissionTypeServiceClient
import retrofit2.Response

class PermissionTypeRepository(private val service: PermissionTypeServiceClient) {
    suspend fun add(dto: PermissionTypeDto): Response<PermissionTypeDto> = service.add(dto)
    suspend fun getAll(): List<PermissionTypeDto> = service.getAll()
    suspend fun update(id: Long, dto: PermissionTypeDto): Response<PermissionTypeDto> = service.update(id, dto)
    suspend fun delete(id: Long): Response<Unit> = service.delete(id)
}


