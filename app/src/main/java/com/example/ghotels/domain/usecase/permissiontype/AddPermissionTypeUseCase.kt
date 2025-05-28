package com.example.ghotels.domain.usecase.permissiontype

import com.example.ghotels.data.model.PermissionTypeDto
import com.example.ghotels.data.repository.PermissionTypeRepository
import com.example.ghotels.domain.model.PermissionType

class AddPermissionTypeUseCase(private val repository: PermissionTypeRepository) {
    suspend operator fun invoke(permissionType: PermissionType): Result<PermissionType> = runCatching {
        val dto = PermissionTypeDto.fromPermissionType(permissionType)
        val response = repository.add(dto)
        if (response.isSuccessful) {
            response.body()?.toPermissionType() ?: throw Exception("Empty response body")
        } else {
            throw Exception("Failed to add permission type")
        }
    }
}
