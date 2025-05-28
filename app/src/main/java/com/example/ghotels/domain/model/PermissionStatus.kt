package com.example.ghotels.domain.model

enum class PermissionStatus(val description: String) {
    PENDING("Pendiente de aprobación"),
    APPROVED("Aprobado por el administrador"),
    REJECTED("Solicitud denegada")
}

