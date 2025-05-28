package com.example.ghotels.data.model

import com.example.ghotels.domain.model.PermissionType

data class PermissionTypeDto(
    val id: Long? = null,
    val name: String,
    val description: String,
    val requiresApproval: Boolean,
    val unlimited: Boolean,
    val annualAvailableDays: Int?
) {
    fun toPermissionType(): PermissionType = PermissionType(
        id = id,
        name = name,
        description = description,
        requiresApproval = requiresApproval,
        unlimited = unlimited,
        annualAvailableDays = annualAvailableDays
    )

    companion object {
        fun fromPermissionType(model: PermissionType): PermissionTypeDto = PermissionTypeDto(
            id = model.id,
            name = model.name,
            description = model.description,
            requiresApproval = model.requiresApproval,
            unlimited = model.unlimited,
            annualAvailableDays = model.annualAvailableDays
        )
    }
}


