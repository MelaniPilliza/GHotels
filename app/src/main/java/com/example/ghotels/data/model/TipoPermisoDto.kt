package com.example.ghotels.data.model

data class TipoPermisoDto(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String,
    val requiereAprobacion: Boolean
)
