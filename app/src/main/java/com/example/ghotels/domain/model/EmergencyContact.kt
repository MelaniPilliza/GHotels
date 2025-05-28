package com.example.ghotels.domain.model

data class EmergencyContact(
    val id: Long? = null,
    val name: String,
    val phone: String,
    val relationship: String,
    val employeeId: Long
)

