package com.example.ghotels.domain.model

data class TipoPermiso(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String,
    val requiereAprobacion: Boolean,
    val ilimitado: Boolean,
    val diasDisponiblesAnuales: Int? = null
)

