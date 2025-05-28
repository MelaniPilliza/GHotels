package com.example.ghotels.domain.model

data class PermissionType(
    val id: Long? = null,
    val name: String,
    val description: String,
    val requiresApproval: Boolean,
    val unlimited: Boolean,
    val annualAvailableDays: Int? = null
)

