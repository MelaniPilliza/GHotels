package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Role

data class RoleDto(
    val id: Long?,
    val name: String,
    val description: String
) {
    fun toRole(): Role = Role(
        id = id,
        name = name,
        description = description
    )

    companion object {
        fun from(role: Role) = RoleDto(
            id = role.id,
            name = role.name,
            description = role.description
        )
    }
}
