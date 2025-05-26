package com.example.ghotels.data.model

data class AsistenciaDto(
    val id: Long? = null,
    val entrada: String,
    val salida: String? = null,
    val motivoNoCumplimiento: String? = null,
    val empleadoId: Long
)

