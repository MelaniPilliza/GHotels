package com.example.ghotels.data.model

data class ContactoEmergenciaDto(
    val id: Long? = null,
    val nombre: String,
    val telefono: String,
    val relacion: String,
    val empleadoId: Long
)
