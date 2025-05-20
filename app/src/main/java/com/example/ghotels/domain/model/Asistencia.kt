package com.example.ghotels.domain.model

data class Asistencia(
    val id: Long? = null,
    val entrada: String,
    val salida: String,
    val motivoNoCumplimiento: String? = null,
    val empleadoId: Long
)

