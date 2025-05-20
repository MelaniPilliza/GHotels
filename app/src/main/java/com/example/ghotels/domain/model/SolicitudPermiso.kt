package com.example.ghotels.domain.model

data class SolicitudPermiso(
    val id: Long? = null,
    val fechaInicio: String,
    val fechaFin: String,
    val fechaResolucion: String? = null,
    val motivo: String,
    val documentoJustificante: String? = null,
    val estado: EstadoPermiso = EstadoPermiso.PENDIENTE,
    val tipoPermiso: TipoPermiso,
    val empleadoId: Long
)

