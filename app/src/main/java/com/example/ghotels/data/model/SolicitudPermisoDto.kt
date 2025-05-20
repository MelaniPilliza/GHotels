package com.example.ghotels.data.model

data class SolicitudPermisoDto(
    val id: Long? = null,
    val fechaInicio: String,
    val fechaFin: String,
    val fechaResolucion: String? = null,
    val motivo: String,
    val documentoJustificante: String? = null,
    val estado: String, // PENDIENTE, APROBADO, RECHAZADO
    val tipoPermisoId: Long,
    val empleadoId: Long
)
