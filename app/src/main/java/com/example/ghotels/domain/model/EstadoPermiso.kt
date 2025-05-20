package com.example.ghotels.domain.model

enum class EstadoPermiso(val descripcion: String) {
    PENDIENTE("Pendiente de aprobación"),
    APROBADO("Aprobado por el administrador"),
    RECHAZADO("Solicitud denegada")
}

