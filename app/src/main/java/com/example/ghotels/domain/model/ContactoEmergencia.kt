package com.example.ghotels.domain.model

data class ContactoEmergencia(
    val id: Long? = null,
    val nombre: String,
    val telefono: String,
    val relacion: String,
    val empleadoId: Long
)

