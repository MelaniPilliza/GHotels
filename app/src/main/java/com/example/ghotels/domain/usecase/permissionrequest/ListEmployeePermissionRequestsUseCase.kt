package com.example.ghotels.domain.usecase.permissionrequest

import com.example.ghotels.data.repository.PermissionRequestRepository
import com.example.ghotels.domain.model.PermissionRequest

class ListEmployeePermissionRequestsUseCase(
    private val repository: PermissionRequestRepository
) {
    suspend operator fun invoke(employeeId: Long): Result<List<PermissionRequest>> = runCatching {
        repository.getByEmployeeId(employeeId).map { it.toPermissionRequest() }
    }
}