package com.example.ghotels.domain.usecase.permissionrequest

import com.example.ghotels.data.repository.PermissionRequestRepository
import com.example.ghotels.domain.model.PermissionRequest

class ListPermissionRequestUseCase (
    private val repository: PermissionRequestRepository
) {
    suspend operator fun invoke(): Result<List<PermissionRequest>> = runCatching {
        repository.getAll().map { it.toPermissionRequest() }
    }
}