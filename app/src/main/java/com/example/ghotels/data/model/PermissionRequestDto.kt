package com.example.ghotels.data.model

import com.example.ghotels.domain.model.PermissionRequest

data class PermissionRequestDto(
    val id: Long? = null,
    val startDate: String,
    val endDate: String,
    val resolutionDate: String? = null,
    val reason: String,
    val supportingDocument: String? = null,
    val status: String,
    val permissionTypeId: Long,
    val employeeId: Long
) {
    fun toPermissionRequest(): PermissionRequest = PermissionRequest(
        id = id,
        startDate = startDate,
        endDate = endDate,
        resolutionDate = resolutionDate,
        reason = reason,
        supportingDocument = supportingDocument,
        status = status,
        permissionTypeId = permissionTypeId,
        employeeId = employeeId
    )

    companion object {
        fun from(entity: PermissionRequest): PermissionRequestDto = PermissionRequestDto(
            id = entity.id,
            startDate = entity.startDate,
            endDate = entity.endDate,
            resolutionDate = entity.resolutionDate,
            reason = entity.reason,
            supportingDocument = entity.supportingDocument,
            status = entity.status,
            permissionTypeId = entity.permissionTypeId,
            employeeId = entity.employeeId
        )
    }
}
