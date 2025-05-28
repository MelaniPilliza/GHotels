package com.example.ghotels.data.model

import com.example.ghotels.domain.model.EmergencyContact

data class EmergencyContactDto(
    val id: Long?,
    val name: String,
    val phone: String,
    val relationship: String,
    val employeeId: Long?
) {
    fun toEmergencyContact(): EmergencyContact = EmergencyContact(
        id = id,
        name = name,
        phone = phone,
        relationship = relationship,
        employeeId = employeeId!!
    )

    companion object {
        fun from(c: EmergencyContact) = EmergencyContactDto(
            id = c.id,
            name = c.name,
            phone = c.phone,
            relationship = c.relationship,
            employeeId = c.employeeId
        )
    }
}
