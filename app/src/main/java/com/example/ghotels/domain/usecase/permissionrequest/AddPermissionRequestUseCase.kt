package com.example.ghotels.domain.usecase.permissionrequest

import com.example.ghotels.data.model.PermissionRequestDto
import com.example.ghotels.data.repository.PermissionRequestRepository
import com.example.ghotels.domain.model.PermissionRequest

class AddPermissionRequestUseCase(
    private val repository: PermissionRequestRepository
) {
    suspend operator fun invoke(permission: PermissionRequest): Boolean {
        val dto = PermissionRequestDto.from(permission)
        return repository.add(dto)
    }
}

