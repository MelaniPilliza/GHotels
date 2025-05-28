package com.example.ghotels.data.repository

import com.example.ghotels.data.model.PermissionRequestDto
import com.example.ghotels.data.source.remote.PermissionRequestServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class PermissionRequestRepository(
    private val serviceClient: PermissionRequestServiceClient
) {
    suspend fun getAll(): List<PermissionRequestDto> {
        return serviceClient.getall()
    }

    suspend fun getByEmployeeId(employeeId: Long): List<PermissionRequestDto> {
        return serviceClient.listEmployeeById(employeeId)
    }

    suspend fun add(dto: PermissionRequestDto): Boolean {
        return serviceClient.add(dto).isSuccessful
    }

    suspend fun approve(id: Long): Boolean {
        return serviceClient.approve(id).isSuccessful
    }

    suspend fun reject(id: Long): Boolean {
        return serviceClient.reject(id).isSuccessful
    }
}
