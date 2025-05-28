package com.example.ghotels.domain.model

data class PermissionRequest(
    val id: Long? = null,
    val startDate: String,
    val endDate: String,
    val resolutionDate: String?,
    val reason: String,
    val supportingDocument: String? = null,
    val status: String = "PENDING",
    val permissionTypeId: Long,
    val employeeId: Long
)

